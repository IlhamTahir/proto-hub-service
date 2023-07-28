package com.bilitech.api.proto.entity;

import com.bilitech.api.core.entity.TraceableBaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Stage extends TraceableBaseEntity {
    private String title;

    @Column(unique = true)
    private String code;

    private Integer sort = 0;
}
