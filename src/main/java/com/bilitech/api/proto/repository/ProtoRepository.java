package com.bilitech.api.proto.repository;

import com.bilitech.api.proto.entity.Project;
import com.bilitech.api.proto.entity.Proto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProtoRepository extends JpaRepository<Proto, String>, JpaSpecificationExecutor<Proto> {
    Optional<Proto> findByProjectAndId(Project project, String id);
}
