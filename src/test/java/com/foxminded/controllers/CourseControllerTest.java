package com.foxminded.controllers;

import com.foxminded.dto.CourseDTO;
import com.foxminded.services.CourseService;
import com.foxminded.testconfig.WebTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ContextConfiguration(classes = CourseController.class)
@WebMvcTest
class CourseControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CourseService courseService;

    private MockMvc mockMvc;

    @BeforeEach
    void set() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void create() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(courseService.create(Mockito.any())).thenReturn(new CourseDTO("matem",1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/createCourse",new CourseDTO("matem",1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-course"));
    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(courseService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/allCourses"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-course"));
    }

    @Test
    void update() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(courseService.update(Mockito.any(),Mockito.any())).thenReturn(new CourseDTO("matem",1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/updateCourse"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-course"));
    }

    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/deleteCourse",new CourseDTO("matem",1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-course"));

    }
}