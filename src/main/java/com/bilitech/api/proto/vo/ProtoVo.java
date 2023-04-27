package com.bilitech.api.proto.vo;

import com.bilitech.api.core.vo.BaseVo;
import com.bilitech.api.proto.dto.ProjectDto;
import com.bilitech.api.proto.enums.ProtoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProtoVo extends BaseVo {
    private String name;

    private ProtoStatus status;

    private String lastVersionId;


    private Integer lastVersionNumber;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastVersionUpdatedTime;

    private String lastVersionLog;

    private Integer baselineVersionNumber;

}
