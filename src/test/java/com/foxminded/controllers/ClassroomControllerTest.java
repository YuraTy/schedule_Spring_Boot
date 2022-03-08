package com.foxminded.controllers;

import com.foxminded.dto.ClassroomDTO;
import com.foxminded.services.ClassroomService;
import com.foxminded.testconfig.TestConfig;
import com.foxminded.testconfig.WebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={WebTestConfig.class})
@WebAppConfiguration
@WebMvcTest(ClassroomController.class)
class ClassroomControllerTest {

    @MockBean
    private ClassroomService classroomService;

    @Test
    void create() throws Exception {
        Mockito.when(classroomService.create(Mockito.any())).thenReturn(new ClassroomDTO(22,1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/createClassroom"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createClassroom"));
    }

    @Test
    void saveClassroom() {
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/allClassrooms"))
                //.andExpect(status().isOk())
                .andExpect(view().name("/"));
    }

    @Test
    void update() {
    }
}