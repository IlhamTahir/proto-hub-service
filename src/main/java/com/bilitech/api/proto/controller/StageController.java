package com.bilitech.api.proto.controller;

import com.bilitech.api.proto.dto.StageCreateRequest;
import com.bilitech.api.proto.mapper.StageMapper;
import com.bilitech.api.proto.service.StageService;
import com.bilitech.api.proto.vo.StageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stages")
@Validated
public class StageController {

    StageService stageService;

    StageMapper stageMapper;

    @PostMapping
    StageVo create(@RequestBody StageCreateRequest stageCreateRequest) {
        return stageMapper.toVo(stageService.create(stageCreateRequest));
    }

    @GetMapping
    List<StageVo> list() {
        return stageService.list().stream().map(stageMapper::toVo).collect(Collectors.toList());
    }



    @Autowired
    public void setStageService(StageService stageService) {
        this.stageService = stageService;
    }

    @Autowired
    public void setStageMapper(StageMapper stageMapper) {
        this.stageMapper = stageMapper;
    }
}
