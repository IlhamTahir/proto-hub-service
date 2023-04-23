package com.bilitech.api.proto.repository;

import com.bilitech.api.proto.entity.Proto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProtoRepository extends JpaRepository<Proto, String>, JpaSpecificationExecutor<Proto> {
}
