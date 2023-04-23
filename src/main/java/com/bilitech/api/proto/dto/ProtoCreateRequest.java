package com.bilitech.api.proto.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProtoCreateRequest {
    @NotBlank(message = "请填写迭代名称")
    @Size(min = 4, max = 128, message = "迭代名称长度应该在4个字符到128个字符之间")
    private String name;
}
