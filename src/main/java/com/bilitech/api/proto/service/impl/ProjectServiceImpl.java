package com.bilitech.api.proto.service.impl;

import com.bilitech.api.core.dto.PageResult;
import com.bilitech.api.core.exception.BizException;
import com.bilitech.api.core.exception.ExceptionType;
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
import com.bilitech.api.proto.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    public Page<ProjectDto> page(ProjectPageFilter projectPageFilter) {
        ProjectSpecification specification = new ProjectSpecification();
        return repository.findAll(specification, projectPageFilter.toPageable()).map(mapper::toDto);
    }

    @Override
    public ProtoDto createProto(String id, ProtoCreateRequest protoCreateRequest) {
        Optional<Project> optionalProject = repository.findById(id);
        if (!optionalProject.isPresent()) {
            throw new BizException(ExceptionType.NOT_FOUND);
        }
        Proto proto = protoMapper.createEntity(protoCreateRequest);
        proto.setCreatedBy(getCurrentUserEntity());
        proto.setUpdatedBy(getCurrentUserEntity());
        proto.setProject(optionalProject.get());
        return protoMapper.toDto(protoRepository.save(proto));
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
