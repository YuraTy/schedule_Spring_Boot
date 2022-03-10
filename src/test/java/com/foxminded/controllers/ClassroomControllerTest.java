package com.foxminded.controllers;

import com.foxminded.dto.ClassroomDTO;
import com.foxminded.services.ClassroomService;
import com.foxminded.testconfig.WebTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ContextConfiguration(classes={WebTestConfig.class})
@WebMvcTest(ClassroomController.class)
class ClassroomControllerTest {

    @MockBean
    private ClassroomService classroomService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(classroomService.create(Mockito.any())).thenReturn(new ClassroomDTO(22,1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/createClassroom"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/create-classroom"));
    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(classroomService.create(Mockito.any())).thenReturn(new ClassroomDTO(22,1));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/allClassrooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("/create-classroom"));
    }

    @Test
    void update() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/updateClassroom"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/create-classroom"));
    }

    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/deleteClassroom"))
                .andExpect(status().isOk());
    }
}