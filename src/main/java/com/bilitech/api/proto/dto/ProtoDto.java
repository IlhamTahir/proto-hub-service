package com.bilitech.api.proto.dto;

import com.bilitech.api.core.dto.TraceableBaseDto;
import com.bilitech.api.proto.enums.ProtoStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@EqualsAndHashCode(callSuper = false)
@Data
public class ProtoDto extends TraceableBaseDto {
    private String name;

    private ProtoStatus status;

    private Integer lastVersionNumber;

    private Date lastVersionUpdatedTime;

    private String lastVersionLog;

    private Integer baselineVersionNumber;
}
