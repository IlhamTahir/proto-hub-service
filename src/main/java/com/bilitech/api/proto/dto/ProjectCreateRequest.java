package com.bilitech.api.proto.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProjectCreateRequest {

    @NotBlank(message = "请填写项目名称")
    @Size(min = 4, max = 128, message = "用户名长度应该在4个字符到128个字符之间")
    private String name;

    @NotBlank(message = "产品经理不能为空")
    private String productOwnerId;
}
