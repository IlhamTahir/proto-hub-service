package com.bilitech.api.proto.dto;

import com.bilitech.api.core.dto.TraceableBaseDto;
import com.bilitech.api.core.dto.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectDto extends TraceableBaseDto {

    private String name;

    private UserDto productOwner;

}
