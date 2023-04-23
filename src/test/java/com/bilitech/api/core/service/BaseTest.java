package com.bilitech.api.core.service;

import com.bilitech.api.core.dto.UserCreateRequest;
import com.bilitech.api.core.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseTest {
    @Autowired
    UserService userService;
    @BeforeEach
    void initAdminUser() {
        createTestUser("admin", "admin");
    }


    protected UserDto createTestUser(String username, String password) {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setUsername(username);
        userCreateRequest.setPassword(password);
        return userService.create(userCreateRequest);
    }
}
