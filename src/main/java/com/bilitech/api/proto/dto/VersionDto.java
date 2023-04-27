package com.bilitech.api.proto.dto;

import com.bilitech.api.core.dto.FileDto;
import com.bilitech.api.core.dto.TraceableBaseDto;
import com.bilitech.api.core.entity.File;
import com.bilitech.api.proto.entity.Proto;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
public class VersionDto extends TraceableBaseDto {
    private Integer number;

    private String log;

    private ProtoDto proto;

    private FileDto file;
}
