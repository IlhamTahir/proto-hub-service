package com.bilitech.api.proto.repository;

import com.bilitech.api.proto.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}
