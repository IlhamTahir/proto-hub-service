package com.bilitech.api.proto.service.impl;

import com.bilitech.api.core.dto.PageResult;
import com.bilitech.api.core.exception.BizException;
import com.bilitech.api.core.exception.ExceptionType;
import com.bilitech.api.core.repository.specs.SearchCriteria;
import com.bilitech.api.core.repository.specs.SearchOperation;
import com.bilitech.api.core.service.impl.BaseService;
import com.bilitech.api.proto.dto.*;
import com.bilitech.api.proto.entity.Project;
import com.bilitech.api.proto.entity.Proto;
import com.bilitech.api.proto.enums.ProjectStatus;
import com.bilitech.api.proto.mapper.ProjectMapper;
import com.bilitech.api.proto.mapper.ProtoMapper;
import com.bilitech.api.proto.repository.ProjectRepository;
import com.bilitech.api.proto.repository.ProtoRepository;
import com.bilitech.api.proto.repository.specs.ProjectSpecification;
import com.bilitech.api.proto.repository.specs.ProtoSpecification;
import com.bilitech.api.proto.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {

    private ProjectRepository repository;

    private ProjectMapper mapper;

    private ProtoMapper protoMapper;

    private ProtoRepository protoRepository;


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
        Optional<Proto> optionalProto = protoRepository.findByProjectAndId(project, protoId);
        if (!optionalProto.isPresent()) {
            throw new BizException(ExceptionType.NOT_FOUND);
        }
        return protoMapper.toDto(optionalProto.get());
    }

    Project getProjectEntity(String id) {
        Optional<Project> optionalProject = repository.findById(id);
        if (!optionalProject.isPresent()) {
            throw new BizException(ExceptionType.NOT_FOUND);
        }
        return optionalProject.get();
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
    public void setProtoRepository(ProtoRepository protoRepository) {
        this.protoRepository = protoRepository;
    }
}
