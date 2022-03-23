package com.foxminded.controllers;

import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.TeacherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TeacherController.class)
@WebMvcTest
class TeacherControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TeacherService teacherService;

    private MockMvc mockMvc;

    @BeforeEach
    void set() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void create() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(teacherService.create(Mockito.any())).thenReturn(new TeacherDTO("Vova", "Tesluk", 1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/create-teacher",new TeacherDTO("Vova", "Tesluk", 1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-teacher"));
    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(teacherService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/all-teachers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-teacher"));
    }

    @Test
    void update() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(teacherService.update(Mockito.any(), Mockito.any())).thenReturn(new TeacherDTO("Vova", "Ivanov", 1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/update-teacher",new TeacherDTO("Vova", "Tesluk", 1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-teacher"));
    }

    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/delete-teacher",new TeacherDTO("Vova", "Tesluk", 1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-teacher"));
    }
}