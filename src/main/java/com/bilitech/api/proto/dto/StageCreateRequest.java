package com.bilitech.api.proto.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StageCreateRequest {
    @NotBlank(message = "请填写名称")
    private String title;

    @NotBlank(message = "请填写编号")
    private String code;

    private Integer sort;
}
