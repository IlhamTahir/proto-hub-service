package com.bilitech.api.proto.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class VersionCreateRequest {

    @NotBlank(message = "请填写更新日志")
    @Size(min = 10, message = "更新日志长度应该大于10个字符")
    private String log;

    @NotBlank(message = "请选择原型文件")
    private String fileId;
}
