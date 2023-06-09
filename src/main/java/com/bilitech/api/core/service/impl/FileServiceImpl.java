package com.bilitech.api.core.service.impl;

import com.bilitech.api.core.dto.FileDto;
import com.bilitech.api.core.dto.FileUploadDto;
import com.bilitech.api.core.dto.FileUploadRequest;
import com.bilitech.api.core.entity.File;
import com.bilitech.api.core.enums.FileStatus;
import com.bilitech.api.core.enums.Storage;
import com.bilitech.api.core.exception.BizException;
import com.bilitech.api.core.exception.ExceptionType;
import com.bilitech.api.core.mapper.FileMapper;
import com.bilitech.api.core.repository.FileRepository;
import com.bilitech.api.core.service.FileService;
import com.bilitech.api.core.service.StorageService;
import com.bilitech.api.core.utils.FileTypeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileServiceImpl extends BaseService implements FileService {

    private Map<String, StorageService> storageServices;

    private FileRepository repository;

    private FileMapper mapper;

    @Override
    @Transactional
    public FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException {
        // 创建File实体
        File file = mapper.createEntity(fileUploadRequest);
        file.setType(FileTypeTransformer.getFileTypeFromExt(fileUploadRequest.getExt()));
        file.setStorage(getDefaultStorage());
        file.setCreatedBy(getCurrentUserEntity());
        file.setUpdatedBy(getCurrentUserEntity());
        File savedFile = repository.save(file);
        // 通过接口获取STS令牌
        FileUploadDto fileUploadDto = storageServices.get(getDefaultStorage().name()).initFileUpload();

        fileUploadDto.setKey(savedFile.getKey());
        fileUploadDto.setFileId(savedFile.getId());
        return fileUploadDto;
    }

    @Override
    public FileDto finishUpload(String id) {
        File file = getFileEntity(id);
        // Todo: 是否是SUPER_ADMIN
        if (!Objects.equals(file.getCreatedBy().getId(), getCurrentUserEntity().getId())) {
            throw new BizException(ExceptionType.FILE_NOT_PERMISSION);
        }

        // Todo: 验证远程文件是否存在

        file.setStatus(FileStatus.UPLOADED);
        return mapper.toDto(repository.save(file));
    }

    // Todo: 后台设置当前Storage
    public Storage getDefaultStorage() {
        return Storage.LOCAL;
    }

    @Override
    public void upload(String id, MultipartFile file) {
        File fileEntity = getFileEntity(id);
        if(!fileEntity.getStatus().equals(FileStatus.UPLOADING)) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
        try {
            storageServices.get(getDefaultStorage().name()).upload(file);
        } catch (Exception e) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
        fileEntity.setStatus(FileStatus.UPLOADED);
        repository.save(fileEntity);
    }

    @Override
    public File getFileEntity(String id) {
        Optional<File> fileOptional = repository.findById(id);
        if (!fileOptional.isPresent()) {
            throw new BizException(ExceptionType.FILE_NOT_FOUND);
        }
        return fileOptional.get();
    }


    @Autowired
    public void setStorageServices(Map<String, StorageService> storageServices) {
        this.storageServices = storageServices;
    }

    @Autowired
    public void setRepository(FileRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(FileMapper mapper) {
        this.mapper = mapper;
    }
}
