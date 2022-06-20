package com.foxminded.controllers;

import com.foxminded.dto.CourseDTO;
import com.foxminded.model.Course;
import com.foxminded.services.CourseService;
import com.foxminded.services.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

@ContextConfiguration(classes = CourseController.class)
@WebMvcTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class CourseControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CourseService courseService;

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
        Mockito.when(courseService.create(Mockito.any())).thenReturn(new CourseDTO("matem",1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/course/create-course")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nameCourse","History"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-course-create"));
    }

    @Test
    void getCreate() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/course/create-course",new Course("matem",1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-course-create"));
    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(courseService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/course/all-course"))
                .andExpect(status().isOk())
                .andExpect(view().name("page-course-create"));
    }

    @Test
    void update() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(courseService.update(Mockito.any(),Mockito.any())).thenReturn(new CourseDTO("matem",1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/course/update-course"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-course-update"));
    }

    @Test
    void getUpdate() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/course/update-course"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-course-update"));
    }

    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/course/delete-course")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nameCourse","History"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-course-delete"));

    }

    @Test
    void getDelete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/course/delete-course",new CourseDTO("matem",1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-course-delete"));

    }
}