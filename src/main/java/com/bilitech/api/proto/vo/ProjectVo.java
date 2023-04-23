package com.bilitech.api.proto.vo;

import com.bilitech.api.core.dto.UserDto;
import com.bilitech.api.core.vo.BaseVo;
import com.bilitech.api.core.vo.UserVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectVo extends BaseVo {
    private String name;

    private UserVo productOwner;
}
