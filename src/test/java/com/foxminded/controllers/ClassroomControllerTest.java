package com.foxminded.controllers;

import com.foxminded.dto.ClassroomDTO;
import com.foxminded.model.Classroom;
import com.foxminded.services.ClassroomService;
import com.foxminded.services.ScheduleService;
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

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
@ContextConfiguration(classes = {ClassroomController.class})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ClassroomControllerTest {

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @MockBean
    private ClassroomService classroomService;

    @MockBean
    private ScheduleService scheduleService;

    private MockMvc mockMvc;

    @Test
    void create() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(classroomService.create(Mockito.any())).thenReturn(new ClassroomDTO(22,1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/classroom/create-classroom",new ClassroomDTO(22,1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-classroom-create"));
    }

    @Test
    void getCreate() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/classroom/create-classroom"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-classroom-create"));

    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(classroomService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/classroom/all-classroom"))
                .andExpect(status().isOk())
                .andExpect(view().name( "page-classroom-create"));
    }

    @Test
    void update() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(classroomService.update(Mockito.any(),Mockito.any())).thenReturn(new ClassroomDTO(22,1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/classroom/update-classroom",33,2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-classroom-update"));
    }

    @Test
    void getUpdate() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/classroom/update-classroom"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-classroom-update"));
    }


    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/classroom/delete-classroom",new Classroom(22,2)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-classroom-delete"));
    }

    @Test
    void getDelete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/classroom/delete-classroom",new Classroom(22,2)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-classroom-delete"));
    }
}