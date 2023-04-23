package com.bilitech.api.proto.controller;

import com.bilitech.api.core.dto.PageResult;
import com.bilitech.api.proto.dto.ProjectCreateRequest;
import com.bilitech.api.proto.dto.ProjectPageFilter;
import com.bilitech.api.proto.dto.ProtoCreateRequest;
import com.bilitech.api.proto.dto.ProtoPageFilter;
import com.bilitech.api.proto.mapper.ProjectMapper;
import com.bilitech.api.proto.mapper.ProtoMapper;
import com.bilitech.api.proto.service.ProjectService;
import com.bilitech.api.proto.vo.ProjectVo;
import com.bilitech.api.proto.vo.ProtoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    ProjectService projectService;


    ProjectMapper projectMapper;

    ProtoMapper protoMapper;


    @PostMapping
    ProjectVo create(@RequestBody @Validated ProjectCreateRequest projectCreateRequest) {
        return projectMapper.toVo(projectService.create(projectCreateRequest));
    }

    @GetMapping("/{id}")
    ProjectVo get(@PathVariable String id) {
        return projectMapper.toVo(projectService.get(id));
    }

    @GetMapping
    PageResult<ProjectVo> page(@Validated ProjectPageFilter projectPageFilter) {
        return new PageResult<>(projectService.page(projectPageFilter).map(projectMapper::toVo));
    }


    @PostMapping("/{id}/proto")
    ProtoVo createProto(@PathVariable String id, @RequestBody @Validated ProtoCreateRequest protoCreateRequest) {
        return protoMapper.toVo(projectService.createProto(id, protoCreateRequest));
    }

    @GetMapping("/{id}/proto")
    PageResult<ProtoVo> protoPage(@PathVariable String id, @Validated ProtoPageFilter protoPageFilter) {
        return new PageResult<>(projectService.protoPage(id, protoPageFilter).map(protoMapper::toVo));
    }

    @GetMapping("/{id}/proto/{protoId}")
    ProtoVo getProto(@PathVariable String id, @PathVariable String protoId) {
        return protoMapper.toVo(projectService.getProto(id, protoId));
    }


    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setProjectMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Autowired
    public void setProtoMapper(ProtoMapper protoMapper) {
        this.protoMapper = protoMapper;
    }
}
