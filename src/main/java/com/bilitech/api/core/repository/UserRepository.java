package com.bilitech.api.core.repository;

import com.bilitech.api.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User getByUsername(String username);

    Optional<User> findByUsername(String username);


    User getById(String id);

}
