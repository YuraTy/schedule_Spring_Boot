package com.foxminded.restcontrollers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.TeacherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class TeacherRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    private TeacherDTO teacherTest() {
        return new TeacherDTO("Ivan","Ivanov",1);
    }

    @Test
    void create() throws Exception {
        Mockito.when(teacherService.create(Mockito.any())).thenReturn(teacherTest());
        String inputJson = mapToJson(teacherTest());

        String uri = "/api/teacher";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        TeacherDTO teacherActual = mapFromJson(content,TeacherDTO.class);
        Assertions.assertEquals(teacherTest(),teacherActual);

    }

    @Test
    void findAll() throws Exception  {
        List<TeacherDTO> list = new ArrayList<>();
        list.add(teacherTest());
        Mockito.when(teacherService.findAll()).thenReturn(list);
        String uri = "/api/teacher";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        TeacherDTO[] teacherDTOS = mapFromJson(content,TeacherDTO[].class);
        Assertions.assertTrue(teacherDTOS.length > 0);

    }

    @Test
    void update() throws Exception  {
        Mockito.when(teacherService.update(Mockito.any(),Mockito.any())).thenReturn(teacherTest());
        String inputJson = mapToJson(teacherTest());

        String uri = "/api/teacher/Ivan/Ivanov";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        TeacherDTO teacherActual = mapFromJson(content, TeacherDTO.class);
        Assertions.assertEquals(teacherTest(),teacherActual);
    }

    @Test
    void delete() throws Exception {
        String uri = "/api/teacher/Ivan/Ivanov";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
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