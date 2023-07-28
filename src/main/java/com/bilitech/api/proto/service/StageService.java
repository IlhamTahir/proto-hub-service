package com.bilitech.api.proto.service;


import com.bilitech.api.proto.dto.StageCreateRequest;
import com.bilitech.api.proto.dto.StageDto;
import com.bilitech.api.proto.vo.StageVo;

import java.util.List;

public interface StageService {
    StageDto create(StageCreateRequest stageCreateRequest);

    List<StageDto> list();
}
