package com.bilitech.api.proto.entity;

import com.bilitech.api.core.entity.File;
import com.bilitech.api.core.entity.TraceableBaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private String demoPath;
}
