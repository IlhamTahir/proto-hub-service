package com.bilitech.api.proto.repository;

import com.bilitech.api.proto.entity.Project;
import com.bilitech.api.proto.entity.Proto;
import com.bilitech.api.proto.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VersionRepository extends JpaRepository<Version, String>, JpaSpecificationExecutor<Version> {
    Optional<Version> findFirstByProtoIdOrderByCreatedTimeDesc(String protoId);

    Optional<Version> findByProtoAndId(Proto project, String id);

}
