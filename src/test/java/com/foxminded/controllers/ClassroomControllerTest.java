package com.foxminded.controllers;

import com.foxminded.dao.ClassroomDaoImpl;
import com.foxminded.dto.ClassroomDTO;
import com.foxminded.model.Classroom;
import com.foxminded.services.ClassroomService;
import com.foxminded.services.CourseService;
import com.foxminded.testconfig.TestConfig;
import com.foxminded.testconfig.WebTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringJUnitWebConfig(classes = {WebTestConfig.class,ClassroomController.class,ClassroomService.class})
class ClassroomControllerTest {

    @BeforeEach
    void setup(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @MockBean
    private ClassroomService classroomService;

    private MockMvc mockMvc;

    @Test
    void create() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(classroomService.create(Mockito.any())).thenReturn(new ClassroomDTO(22,1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/createClassroom"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-classroom"));
    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(classroomService.create(Mockito.any())).thenReturn(new ClassroomDTO(22,1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/allClassrooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-classroom"));
    }

    @Test
    void update() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(classroomService.create(Mockito.any())).thenReturn(new ClassroomDTO(22,1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/updateClassroom",new Classroom(22,2),new Classroom(12,2)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-classroom"));
    }

    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/deleteClassroom",new Classroom(22,2)))
                ;
    }
}