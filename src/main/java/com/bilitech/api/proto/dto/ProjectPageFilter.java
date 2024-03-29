package com.bilitech.api.proto.dto;

import com.bilitech.api.core.dto.BaseSearchFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProjectPageFilter extends BaseSearchFilter {
    private String filterType = "all";
}
