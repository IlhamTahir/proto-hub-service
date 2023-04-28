package com.bilitech.api.proto.dto;

import com.bilitech.api.proto.enums.ProtoStatus;
import lombok.Data;

@Data
public class ProtoStatusUpdateRequest {

    private ProtoStatus status;
}
