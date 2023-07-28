package com.bilitech.api.proto.mapper;

import com.bilitech.api.core.mapper.FileMapper;
import com.bilitech.api.core.mapper.MapperInterface;
import com.bilitech.api.core.mapper.UserMapper;
import com.bilitech.api.proto.dto.VersionCreateRequest;
import com.bilitech.api.proto.dto.VersionDto;
import com.bilitech.api.proto.entity.Version;
import com.bilitech.api.proto.vo.VersionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {UserMapper.class, FileMapper.class, StageMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface VersionMapper extends MapperInterface<Version, VersionDto, VersionVo> {

    @Mapping(source = "fileId", target = "file.id")
    @Mapping(source = "stageId", target = "stage.id")
    Version createEntity(VersionCreateRequest versionCreateRequest);
}
