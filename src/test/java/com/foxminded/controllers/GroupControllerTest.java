package com.foxminded.controllers;

import com.foxminded.dto.GroupDTO;
import com.foxminded.services.GroupService;
import com.foxminded.services.ScheduleService;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = GroupController.class)
@WebMvcTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class GroupControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private GroupService groupService;

    @MockBean
    private ScheduleService scheduleService;

    private MockMvc mockMvc;

    @BeforeEach
    void set() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void create() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(groupService.create(Mockito.any())).thenReturn(new GroupDTO("22-DE",1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/group/create-group",new GroupDTO("22-DE",1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-group-create"));
    }

    @Test
    void getCrete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/group/create-group"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-group-create"));
    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(groupService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/group/all-group"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-group-create"));
    }

    @Test
    void update() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(groupService.update(Mockito.any(),Mockito.any())).thenReturn(new GroupDTO("22-DE",1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/group/update-group",new GroupDTO("22-DE",1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-group-update"));
    }

    @Test
    void getUpdate() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/group/update-group"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-group-update"));
    }

    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/group/delete-group",new GroupDTO("22-DE",1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-group-delete"));
    }

    @Test
    void getDelete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/group/delete-group"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-group-delete"));
    }
}