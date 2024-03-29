package com.bilitech.api.proto.service.impl;

import com.bilitech.api.core.entity.File;
import com.bilitech.api.core.exception.BizException;
import com.bilitech.api.core.exception.ExceptionType;
import com.bilitech.api.core.repository.specs.SearchCriteria;
import com.bilitech.api.core.repository.specs.SearchOperation;
import com.bilitech.api.core.service.FileService;
import com.bilitech.api.core.service.impl.BaseService;
import com.bilitech.api.core.utils.ZipUtil;
import com.bilitech.api.proto.dto.*;
import com.bilitech.api.proto.entity.Project;
import com.bilitech.api.proto.entity.Proto;
import com.bilitech.api.proto.entity.Stage;
import com.bilitech.api.proto.entity.Version;
import com.bilitech.api.proto.enums.ProjectStatus;
import com.bilitech.api.proto.enums.ProtoStatus;
import com.bilitech.api.proto.mapper.ProjectMapper;
import com.bilitech.api.proto.mapper.ProtoMapper;
import com.bilitech.api.proto.mapper.VersionMapper;
import com.bilitech.api.proto.repository.ProjectRepository;
import com.bilitech.api.proto.repository.ProtoRepository;
import com.bilitech.api.proto.repository.VersionRepository;
import com.bilitech.api.proto.repository.specs.ProjectSpecification;
import com.bilitech.api.proto.repository.specs.ProtoSpecification;
import com.bilitech.api.proto.repository.specs.VersionSpecification;
import com.bilitech.api.proto.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {

    private ProjectRepository repository;

    private ProjectMapper mapper;

    private ProtoMapper protoMapper;

    private VersionMapper versionMapper;


    private ProtoRepository protoRepository;

    private VersionRepository versionRepository;


    private FileService fileService;

    @Value("${FILE_UPLOAD_DIR}")
    private String fileDir;

    @Override
    public ProjectDto create(ProjectCreateRequest projectCreateRequest) {


        Project project = mapper.createEntity(projectCreateRequest);
        project.setStatus(ProjectStatus.PUBLISHED);
        project.setCreatedBy(getCurrentUserEntity());
        project.setUpdatedBy(getCurrentUserEntity());

        project.setProductOwner(getUserById(projectCreateRequest.getProductOwnerId()));

        Project savedProject = repository.save(project);
        return mapper.toDto(savedProject);
    }


    @Override
    public ProjectDto get(String id) {
        return mapper.toDto(getProjectEntity(id));
    }

    @Override
    public Page<ProjectDto> page(ProjectPageFilter projectPageFilter) {
        ProjectSpecification specification = new ProjectSpecification();

        if (Objects.equals(projectPageFilter.getFilterType(), "onlyMe")) {
            specification.add(new SearchCriteria("productOwner",getCurrentUserEntity(), SearchOperation.EQUAL ));
        }
        return repository.findAll(specification, projectPageFilter.toPageable()).map(mapper::toDto);
    }

    @Override
    public ProtoDto createProto(String id, ProtoCreateRequest protoCreateRequest) {
        Project project = getProjectEntity(id);
        Proto proto = protoMapper.createEntity(protoCreateRequest);
        proto.setCreatedBy(getCurrentUserEntity());
        proto.setUpdatedBy(getCurrentUserEntity());
        proto.setProject(project);
        return protoMapper.toDto(protoRepository.save(proto));
    }

    @Override
    public Page<ProtoDto> protoPage(String id, ProtoPageFilter protoPageFilter) {
        Project project = getProjectEntity(id);

        ProtoSpecification specs = new ProtoSpecification();

        specs.add(new SearchCriteria("project", project, SearchOperation.EQUAL));

        // ToDo: 需要重构
        if (!Objects.equals(protoPageFilter.getStatus(), null)) {
            specs.add(new SearchCriteria("status", protoPageFilter.getStatus(), SearchOperation.EQUAL));
        }


        return protoRepository.findAll(specs, protoPageFilter.toPageable()).map(protoMapper::toDto);
    }

    @Override
    public ProtoDto getProto(String id, String protoId) {
        Project project = getProjectEntity(id);
        Proto proto = getProtoEntity(project, protoId);
        return protoMapper.toDto(proto);
    }

    @Override
    public VersionDto createVersion(String id, String protoId, VersionCreateRequest versionCreateRequest) throws IOException {
        Project project = getProjectEntity(id);
        Proto proto = getProtoEntity(project, protoId);
        Version version = versionMapper.createEntity(versionCreateRequest);
        version.setCreatedBy(getCurrentUserEntity());
        version.setUpdatedBy(getCurrentUserEntity());
        version.setProto(proto);



        Optional<Version> lastVersion = versionRepository.findFirstByProtoIdOrderByCreatedTimeDesc(protoId);
        int number = 1;
        if(lastVersion.isPresent()) {
            number = lastVersion.get().getNumber() + 1;
        }
        version.setNumber(number);

        File file = fileService.getFileEntity(version.getFile().getId());

        System.out.println(file);

        String sourceFileDest = fileDir + "/" + file.getName();
        String targetDemoDest = fileDir + "/demo/" + project.getId() + "/" + proto.getId() + "/" + number;

        String indexPath = ZipUtil.unzipAndGetIndexPath(sourceFileDest, targetDemoDest);

        version.setDemoPath(indexPath.replaceAll(fileDir, ""));

        Version savedVersion = versionRepository.saveAndFlush(version);
        proto.setLastVersionId(savedVersion.getId());
        proto.setLastVersionNumber(savedVersion.getNumber());
        proto.setLastVersionUpdatedTime(savedVersion.getCreatedTime());
        proto.setLastVersionLog(savedVersion.getLog());
        protoRepository.save(proto);



        return versionMapper.toDto(savedVersion);
    }

    @Override
    public Page<VersionDto> versionPage(String id, String protoId, VersionPageFilter versionPageFilter) {
        Project project = getProjectEntity(id);
        Proto proto = getProtoEntity(project, protoId);
        VersionSpecification specs = new VersionSpecification();
        specs.add(new SearchCriteria("proto", proto, SearchOperation.EQUAL));

        return versionRepository.findAll(specs, versionPageFilter.toPageable()).map(versionMapper::toDto);
    }


    @Override
    public void updateProtoStatus(String id, String protoId, ProtoStatus protoStatus) {
        Project project = getProjectEntity(id);
        Proto proto = getProtoEntity(project, protoId);

        proto.setStatus(protoStatus);
        protoRepository.save(proto);
    }

    @Override
    public void setBaselineVersion(String id, String protoId, String versionId) {
        Project project = getProjectEntity(id);
        Proto proto = getProtoEntity(project, protoId);
        Version version = getVersionEntity(proto, versionId);
        proto.setBaselineVersionNumber(version.getNumber());
        protoRepository.save(proto);
    }

    @Override
    public VersionDto getVersion(String id, String protoId, String versionId) {
        Project project = getProjectEntity(id);
        Proto proto = getProtoEntity(project, protoId);
        Version version = getVersionEntity(proto, versionId);
        return versionMapper.toDto(version);
    }

    Project getProjectEntity(String id) {
        Optional<Project> optionalProject = repository.findById(id);
        if (!optionalProject.isPresent()) {
            throw new BizException(ExceptionType.NOT_FOUND);
        }
        return optionalProject.get();
    }

    Proto getProtoEntity(Project project, String protoId) {
        Optional<Proto> optionalProto = protoRepository.findByProjectAndId(project, protoId);
        if (!optionalProto.isPresent()) {
            throw new BizException(ExceptionType.NOT_FOUND);
        }

        return optionalProto.get();
    }

    Version getVersionEntity(Proto proto, String versionId) {
        Optional<Version> optionalVersion = versionRepository.findByProtoAndId(proto, versionId);
        if (!optionalVersion.isPresent()) {
            throw new BizException(ExceptionType.NOT_FOUND);
        }
        return optionalVersion.get();
    }

    @Override
    public List<VersionDto> findVersionListInStageIds(String id, String protoId,List<String> stageIds) {
        List<Stage> stages = new ArrayList<>();
        for (String stageId :stageIds) {
            Stage stage = new Stage();
            stage.setId(stageId);
            stages.add(stage);
        }
        Proto proto = getProtoEntity(getProjectEntity(id), protoId);
        return versionRepository.findAllByProtoAndStageIn(proto, stages).stream().map(versionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public VersionDto getVersionByStageId(String id, String protoId,String stageId) {
        Stage stage = new Stage();
        stage.setId(stageId);
        Proto proto = getProtoEntity(getProjectEntity(id), protoId);

        return versionMapper.toDto(versionRepository.getFirstByProtoAndStageOrderByCreatedTimeDesc(proto,stage));
    }

    @Autowired
    public void setRepository(ProjectRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(ProjectMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setProtoMapper(ProtoMapper protoMapper) {
        this.protoMapper = protoMapper;
    }

    @Autowired
    public void setVersionMapper(VersionMapper versionMapper) {
        this.versionMapper = versionMapper;
    }

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setProtoRepository(ProtoRepository protoRepository) {
        this.protoRepository = protoRepository;
    }
}
