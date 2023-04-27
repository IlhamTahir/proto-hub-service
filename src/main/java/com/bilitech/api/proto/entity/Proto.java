package com.bilitech.api.proto.entity;

import com.bilitech.api.core.entity.TraceableBaseEntity;
import com.bilitech.api.proto.enums.ProtoStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Proto extends TraceableBaseEntity {
    private String name;


    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProtoStatus status = ProtoStatus.TO_DEVELOP;

    private String lastVersionId;

    private Integer lastVersionNumber;

    private Date lastVersionUpdatedTime;

    private String lastVersionLog;

    private Integer baselineVersionNumber;

}
