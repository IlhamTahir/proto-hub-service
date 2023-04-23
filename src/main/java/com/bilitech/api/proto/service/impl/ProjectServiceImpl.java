package com.bilitech.api.proto.service.impl;

import com.bilitech.api.core.exception.BizException;
import com.bilitech.api.core.exception.ExceptionType;
import com.bilitech.api.core.service.impl.BaseService;
import com.bilitech.api.proto.dto.ProjectCreateRequest;
import com.bilitech.api.proto.dto.ProjectDto;
import com.bilitech.api.proto.entity.Project;
import com.bilitech.api.proto.enums.ProjectStatus;
import com.bilitech.api.proto.mapper.ProjectMapper;
import com.bilitech.api.proto.repository.ProjectRepository;
import com.bilitech.api.proto.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {

    private ProjectRepository repository;

    private ProjectMapper mapper;


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


    @Autowired
    public void setRepository(ProjectRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(ProjectMapper mapper) {
        this.mapper = mapper;
    }
}
