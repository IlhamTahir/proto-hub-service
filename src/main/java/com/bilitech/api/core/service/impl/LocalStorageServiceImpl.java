package com.bilitech.api.core.service.impl;

import com.bilitech.api.core.dto.FileUploadDto;
import com.bilitech.api.core.enums.Storage;
import com.bilitech.api.core.exception.BizException;
import com.bilitech.api.core.exception.ExceptionType;
import com.bilitech.api.core.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("LOCAL")
public class LocalStorageServiceImpl implements StorageService {

    @Value("${FILE_UPLOAD_DIR}")
    private String fileDir;
    @Override
    public FileUploadDto initFileUpload() throws IOException {
        return new FileUploadDto();
    }

    @Override
    public void upload(MultipartFile file) {
        String filename = file.getOriginalFilename();
        Path filepath = Paths.get(fileDir, filename);
        try {
            Files.write(filepath, file.getBytes());
        } catch (IOException e) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
    }

    @Override
    public String getFileUri(String fileKey) {
        return null;
    }
}
