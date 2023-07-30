package com.bilitech.api.proto.controller;

import com.bilitech.api.core.vo.PageVo;
import com.bilitech.api.proto.dto.*;
import com.bilitech.api.proto.mapper.ProjectMapper;
import com.bilitech.api.proto.mapper.ProtoMapper;
import com.bilitech.api.proto.mapper.VersionMapper;
import com.bilitech.api.proto.service.ProjectService;
import com.bilitech.api.proto.vo.ProjectVo;
import com.bilitech.api.proto.vo.ProtoVo;
import com.bilitech.api.proto.vo.VersionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    ProjectService projectService;


    ProjectMapper projectMapper;

    ProtoMapper protoMapper;

    VersionMapper versionMapper;


    @PostMapping
    ProjectVo create(@RequestBody @Validated ProjectCreateRequest projectCreateRequest) {
        return projectMapper.toVo(projectService.create(projectCreateRequest));
    }

    @GetMapping("/{id}")
    ProjectVo get(@PathVariable String id) {
        return projectMapper.toVo(projectService.get(id));
    }

    @GetMapping
    PageVo<ProjectVo> page(@Validated ProjectPageFilter projectPageFilter) {
        return new PageVo<>(projectService.page(projectPageFilter).map(projectMapper::toVo));
    }


    @PostMapping("/{id}/proto")
    ProtoVo createProto(@PathVariable String id, @RequestBody @Validated ProtoCreateRequest protoCreateRequest) {
        return protoMapper.toVo(projectService.createProto(id, protoCreateRequest));
    }

    @GetMapping("/{id}/proto")
    PageVo<ProtoVo> protoPage(@PathVariable String id, @Validated ProtoPageFilter protoPageFilter) {
        return new PageVo<>(projectService.protoPage(id, protoPageFilter).map(protoMapper::toVo));
    }

    @GetMapping("/{id}/proto/{protoId}")
    ProtoVo getProto(@PathVariable String id, @PathVariable String protoId) {
        return protoMapper.toVo(projectService.getProto(id, protoId));
    }

    @PostMapping("/{id}/proto/{protoId}/version")
    VersionVo createVersion(@PathVariable String id,
                            @PathVariable String protoId,
                            @RequestBody @Validated VersionCreateRequest versionCreateRequest) throws IOException {
        return versionMapper.toVo(projectService.createVersion(id, protoId, versionCreateRequest));
    }

    @GetMapping("/{id}/proto/{protoId}/version/{versionId}")
    VersionVo getVersion(@PathVariable String id,
                         @PathVariable String protoId,
                         @PathVariable String versionId) {
        return versionMapper.toVo(projectService.getVersion(id, protoId, versionId));
    }

    @GetMapping("/{id}/proto/{protoId}/version")
    PageVo<VersionVo> versionPage(@PathVariable String id,
                                  @PathVariable String protoId, VersionPageFilter versionPageFilter) {
        return new PageVo<>(projectService.versionPage(id, protoId, versionPageFilter).map(versionMapper::toVo));
    }

    @GetMapping("/{id}/proto/{protoId}/version-in-stage-ids")
    List<VersionVo> versionListInStageIds(@PathVariable String id,
                                          @PathVariable String protoId,
                                          @RequestParam List<String> stageIds) {
        return projectService.findVersionListInStageIds(id, protoId, stageIds).stream().map(versionMapper::toVo).collect(Collectors.toList());
    }

    @GetMapping("/{id}/proto/{protoId}/version-by-stage")
    VersionVo getVersionByStageId(@PathVariable String id,
                                  @PathVariable String protoId,
                                  @RequestParam String stageId) {
        return versionMapper.toVo(projectService.getVersionByStageId(id, protoId, stageId));
    }

    @PostMapping("/{id}/proto/{protoId}/status")
    void updateProtoStatus(@PathVariable String id,
                           @PathVariable String protoId,
                           @RequestBody ProtoStatusUpdateRequest protoStatusUpdateRequest
    ) {
        projectService.updateProtoStatus(id, protoId, protoStatusUpdateRequest.getStatus());
    }

    @PostMapping("/{id}/proto/{protoId}/baseline_version/{versionId}")
    void setBaselineVersion(@PathVariable String id,
                            @PathVariable String protoId,
                            @PathVariable String versionId) {
        projectService.setBaselineVersion(id, protoId, versionId);

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

    @Autowired
    public void setVersionMapper(VersionMapper versionMapper) {
        this.versionMapper = versionMapper;
    }
}
