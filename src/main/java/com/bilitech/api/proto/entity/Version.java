package com.bilitech.api.proto.entity;

import com.bilitech.api.core.entity.File;
import com.bilitech.api.core.entity.TraceableBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
public class Version extends TraceableBaseEntity {

    private Integer number;

    private String log;

    @OneToOne
    @JoinColumn(name = "proto_id")
    private Proto proto;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;
}
