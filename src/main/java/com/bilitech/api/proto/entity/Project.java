package com.bilitech.api.proto.entity;

import com.bilitech.api.core.entity.TraceableBaseEntity;
import com.bilitech.api.core.entity.User;
import com.bilitech.api.proto.enums.ProjectStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Project extends TraceableBaseEntity {

    private String name;


    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_owner_id")
    private User productOwner;
}
