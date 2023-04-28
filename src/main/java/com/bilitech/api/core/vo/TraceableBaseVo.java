package com.bilitech.api.core.vo;

import com.bilitech.api.core.dto.UserDto;
import lombok.Data;

@Data
public class TraceableBaseVo extends BaseVo {

    private UserVo createdBy;

    private UserVo updatedBy;

}
