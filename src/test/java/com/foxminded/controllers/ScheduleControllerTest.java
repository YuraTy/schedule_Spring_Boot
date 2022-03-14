package com.foxminded.controllers;

import com.foxminded.dto.ScheduleDTO;
import com.foxminded.model.Classroom;
import com.foxminded.model.Course;
import com.foxminded.model.Group;
import com.foxminded.model.Teacher;
import com.foxminded.services.ScheduleService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ScheduleController.class)
@WebMvcTest
class ScheduleControllerTest {

    @Autowired
    private WebApplicationContext context;

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
        Mockito.when(scheduleService.create(Mockito.any())).thenReturn(new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/createSchedule",new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-schedule"));
    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(scheduleService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/allSchedule"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-schedule"));

    }

    @Test
    void takeScheduleToTeacher() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(scheduleService.takeScheduleToTeacher(Mockito.any())).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/takeScheduleToTeacher",new Teacher("Ivan", "Ivanov", 1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-schedule"));
    }

    @Test
    void update() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(scheduleService.update(Mockito.any(),Mockito.any())).thenReturn(new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/updateSchedule", new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1),new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Sidorov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-schedule"));
    }

    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/deleteSchedule",new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-schedule"));
    }
}