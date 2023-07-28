package com.bilitech.api.proto.service.impl;

import com.bilitech.api.core.service.impl.BaseService;
import com.bilitech.api.proto.dto.StageCreateRequest;
import com.bilitech.api.proto.dto.StageDto;
import com.bilitech.api.proto.entity.Stage;
import com.bilitech.api.proto.mapper.StageMapper;
import com.bilitech.api.proto.repository.StageRepository;
import com.bilitech.api.proto.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StageServiceImpl extends BaseService implements StageService {

    StageRepository repository;

    StageMapper mapper;
    @Override
    public StageDto create(StageCreateRequest stageCreateRequest) {
        Stage stage = mapper.createEntity(stageCreateRequest);
        stage.setCreatedBy(getCurrentUserEntity());
        stage.setUpdatedBy(getCurrentUserEntity());
        return mapper.toDto(repository.save(stage));
    }

    @Override
    public List<StageDto> list() {
        return repository.findAll(Sort.by("sort")).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Autowired
    public void setRepository(StageRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(StageMapper mapper) {
        this.mapper = mapper;
    }
}
