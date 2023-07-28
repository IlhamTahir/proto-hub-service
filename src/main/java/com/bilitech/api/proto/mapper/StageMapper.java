package com.bilitech.api.proto.mapper;

import com.bilitech.api.core.mapper.MapperInterface;
import com.bilitech.api.core.mapper.UserMapper;
import com.bilitech.api.proto.dto.StageCreateRequest;
import com.bilitech.api.proto.dto.StageDto;
import com.bilitech.api.proto.dto.VersionCreateRequest;
import com.bilitech.api.proto.entity.Stage;
import com.bilitech.api.proto.entity.Version;
import com.bilitech.api.proto.vo.StageVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {UserMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface StageMapper extends MapperInterface<Stage, StageDto, StageVo> {
    Stage createEntity(StageCreateRequest stageCreateRequest);
}
