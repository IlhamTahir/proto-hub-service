package com.bilitech.api.proto.service;

import com.bilitech.api.proto.dto.*;
import com.bilitech.api.proto.enums.ProtoStatus;
import com.bilitech.api.proto.vo.VersionVo;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProjectService {
    ProjectDto create(ProjectCreateRequest projectCreateRequest);

    ProjectDto get(String id);

    Page<ProjectDto> page(ProjectPageFilter projectPageFilter);


    ProtoDto createProto(String id, ProtoCreateRequest protoCreateRequest);

    ProtoDto getProto(String id, String protoId);

    Page<ProtoDto> protoPage(String id, ProtoPageFilter protoPageFilter);

    VersionDto createVersion(String id, String protoId, VersionCreateRequest versionCreateRequest) throws IOException;

    VersionDto getVersion(String id, String protoId, String versionId);

    Page<VersionDto> versionPage(String id, String protoId, VersionPageFilter versionPageFilter);

    void updateProtoStatus(String id, String protoId, ProtoStatus protoStatus);

    void setBaselineVersion(String id, String protoId, String versionId);

    List<VersionDto> findVersionListInStageIds(String id, String protoId,List<String> stageIds);
}
