package com.bilitech.api.proto.service;

import com.bilitech.api.proto.dto.ProjectCreateRequest;
import com.bilitech.api.proto.dto.ProjectDto;

public interface ProjectService {
    ProjectDto create(ProjectCreateRequest projectCreateRequest);
}
