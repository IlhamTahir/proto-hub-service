package com.bilitech.api.proto.repository;


import com.bilitech.api.proto.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, String> {
}
