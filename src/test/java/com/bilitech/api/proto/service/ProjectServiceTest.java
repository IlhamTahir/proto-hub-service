package com.bilitech.api.proto.service;

import com.bilitech.api.core.dto.PageResult;
import com.bilitech.api.core.dto.UserDto;
import com.bilitech.api.core.service.BaseTest;
import com.bilitech.api.proto.dto.*;
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
        String projectName = "测试项目";
        ProjectDto savedProject = createTestProject(projectName);
        assertEquals(projectName, savedProject.getName());
        assertEquals("admin", savedProject.getCreatedBy().getUsername());
        assertEquals("admin", savedProject.getUpdatedBy().getUsername());
    }

    ProjectDto createTestProject(String name) {
        ProjectCreateRequest projectCreateRequest = new ProjectCreateRequest();
        projectCreateRequest.setName(name);
        UserDto userDto = createTestUser("productOwner1", "123456");
        projectCreateRequest.setProductOwnerId(userDto.getId());
        return projectService.create(projectCreateRequest);
    }

    @Test
    @WithMockUser(username = "admin")
    void createProto() {
        ProtoCreateRequest projectCreateRequest = new ProtoCreateRequest();
        projectCreateRequest.setName("测试迭代");
        ProjectDto testProject = createTestProject("测试项目1");
        ProtoDto savedProto = projectService.createProto(testProject.getId(),projectCreateRequest);
        System.out.println(savedProto);
        assertEquals("测试迭代", savedProto.getName());
    }

    @Test
    @WithMockUser(username = "admin")
    void page() {
    }
}