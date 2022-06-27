package com.foxminded.restcontrollers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.dto.CourseDTO;
import com.foxminded.services.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class CourseRestControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    private CourseDTO testCourse() {
        return new CourseDTO("History", 1);
    }

    @Test
    void create() throws Exception {
        Mockito.when(courseService.create(Mockito.any())).thenReturn(testCourse());
        String inputJson = mapToJson(testCourse());

        String uri = "/api/course";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        CourseDTO courseActual = mapFromJson(content, CourseDTO.class);
        Assertions.assertEquals(testCourse(), courseActual);

    }

    @Test
    void findAll() throws Exception {
        List<CourseDTO> list = new ArrayList<>();
        list.add(testCourse());
        Mockito.when(courseService.findAll()).thenReturn(list);
        String uri = "/api/course";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        CourseDTO[] courseDTOS = mapFromJson(content, CourseDTO[].class);
        Assertions.assertTrue(courseDTOS.length > 0);
    }

    @Test
    void update() throws Exception {
        Mockito.when(courseService.update(Mockito.any(), Mockito.any())).thenReturn(testCourse());
        String inputJson = mapToJson(testCourse());

        String uri = "/api/course/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        CourseDTO courseActual = mapFromJson(content, CourseDTO.class);
        Assertions.assertEquals(testCourse(), courseActual);

    }

    @Test
    void delete() throws Exception {
        String uri = "/api/course/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}