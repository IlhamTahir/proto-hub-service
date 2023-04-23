package com.bilitech.api.proto.service;

import com.bilitech.api.core.dto.UserDto;
import com.bilitech.api.core.service.BaseTest;
import com.bilitech.api.proto.dto.ProjectCreateRequest;
import com.bilitech.api.proto.dto.ProjectDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest extends BaseTest {

    @Autowired
    ProjectService projectService;


    @Test
    @WithMockUser(username = "admin")
    void create() {
        ProjectCreateRequest projectCreateRequest = new ProjectCreateRequest();
        projectCreateRequest.setName("测试项目");
        UserDto userDto = createTestUser("productOwner1", "123456");
        projectCreateRequest.setProductOwnerId(userDto.getId());
        ProjectDto savedProject = projectService.create(projectCreateRequest);
        assertEquals(projectCreateRequest.getName(), savedProject.getName());
        assertEquals("admin", savedProject.getCreatedBy().getUsername());
        assertEquals("admin", savedProject.getUpdatedBy().getUsername());
    }
}