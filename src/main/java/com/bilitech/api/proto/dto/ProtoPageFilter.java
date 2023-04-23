package com.bilitech.api.proto.dto;

import com.bilitech.api.core.dto.BaseSearchFilter;
import com.bilitech.api.proto.enums.ProtoStatus;
import lombok.Data;

@Data
public class ProtoPageFilter extends BaseSearchFilter {
    private ProtoStatus status;
}
