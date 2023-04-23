package com.bilitech.api.proto.controller;

import com.bilitech.api.proto.dto.ProjectCreateRequest;
import com.bilitech.api.proto.mapper.ProjectMapper;
import com.bilitech.api.proto.service.ProjectService;
import com.bilitech.api.proto.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    ProjectService projectService;

    ProjectMapper projectMapper;


    @PostMapping
    ProjectVo create(@RequestBody @Validated ProjectCreateRequest projectCreateRequest) {
        return projectMapper.toVo(projectService.create(projectCreateRequest));
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setProjectMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }
}
