package com.bilitech.api.proto.service;

import com.bilitech.api.proto.dto.*;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ProjectService {
    ProjectDto create(ProjectCreateRequest projectCreateRequest);

    ProjectDto get(String id);

    Page<ProjectDto> page(ProjectPageFilter projectPageFilter);


    ProtoDto createProto(String id, ProtoCreateRequest protoCreateRequest);

    ProtoDto getProto(String id, String protoId);

    Page<ProtoDto> protoPage(String id, ProtoPageFilter protoPageFilter);

    VersionDto createVersion(String id, String protoId, VersionCreateRequest versionCreateRequest) throws IOException;

    VersionDto getVersion(String id, String protoId, String versionId);
}
