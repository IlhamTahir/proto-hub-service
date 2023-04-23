package com.bilitech.api.proto.mapper;

import com.bilitech.api.core.mapper.MapperInterface;
import com.bilitech.api.core.mapper.UserMapper;
import com.bilitech.api.proto.dto.ProjectCreateRequest;
import com.bilitech.api.proto.dto.ProjectDto;
import com.bilitech.api.proto.entity.Project;
import com.bilitech.api.proto.vo.ProjectVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = UserMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProjectMapper extends MapperInterface<Project, ProjectDto, ProjectVo> {
    Project createEntity(ProjectCreateRequest projectCreateRequest);

}
