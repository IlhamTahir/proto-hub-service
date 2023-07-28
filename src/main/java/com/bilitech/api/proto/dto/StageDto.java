package com.bilitech.api.proto.dto;

import com.bilitech.api.core.dto.TraceableBaseDto;
import lombok.Data;


@Data
public class StageDto extends TraceableBaseDto {
    private String title;

    private String code;

    private Integer sort;
}
