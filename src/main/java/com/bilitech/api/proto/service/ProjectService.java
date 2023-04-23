package com.bilitech.api.proto.service;

import com.bilitech.api.proto.dto.*;
import org.springframework.data.domain.Page;

public interface ProjectService {
    ProjectDto create(ProjectCreateRequest projectCreateRequest);

    Page<ProjectDto> page(ProjectPageFilter projectPageFilter);


    ProtoDto createProto(String id, ProtoCreateRequest protoCreateRequest);
}
