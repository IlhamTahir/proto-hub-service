package com.bilitech.api.proto.mapper;

import com.bilitech.api.core.mapper.MapperInterface;
import com.bilitech.api.core.mapper.UserMapper;
import com.bilitech.api.proto.dto.ProtoCreateRequest;
import com.bilitech.api.proto.dto.ProtoDto;
import com.bilitech.api.proto.entity.Proto;
import com.bilitech.api.proto.vo.ProtoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = UserMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProtoMapper extends MapperInterface<Proto, ProtoDto, ProtoVo> {

    Proto createEntity(ProtoCreateRequest protoCreateRequest);
}
