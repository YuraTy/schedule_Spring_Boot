package com.foxminded.restcontrollers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.dto.*;
import com.foxminded.services.ScheduleService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ScheduleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    private ClassroomDTO classroomTest() {
        return new ClassroomDTO(1, 1);
    }

    private CourseDTO courseTest() {
        return new CourseDTO("History", 1);
    }

    private GroupDTO groupTest() {
        return new GroupDTO("WW-00", 1);
    }

    private TeacherDTO teacherTest() {
        return new TeacherDTO("Ivan", "Ivanov", 1);
    }

    private ScheduleDTO scheduleTest() {
        return new ScheduleDTO(groupTest(), teacherTest(), courseTest(), classroomTest(), "2022-06-27 18:10:00", "2022-06-27 19:10:00", 1);
    }


    @Test
    void create() throws Exception {
        Mockito.when(scheduleService.create(Mockito.any())).thenReturn(scheduleTest());
        String inputJson = mapToJson(scheduleTest());

        String uri = "/api/schedule";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        ScheduleDTO scheduleActual = mapFromJson(content, ScheduleDTO.class);
        Assertions.assertEquals(scheduleTest(), scheduleActual);
    }

    @Test
    void findAll() throws Exception {
        List<ScheduleDTO> list = new ArrayList<>();
        list.add(scheduleTest());
        Mockito.when(scheduleService.findAll()).thenReturn(list);
        String uri = "/api/schedule";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ScheduleDTO[] scheduleDTOS = mapFromJson(content, ScheduleDTO[].class);
        Assertions.assertTrue(scheduleDTOS.length > 0);
    }

    @Test
    void findScheduleToTeacher() throws Exception {
        List<ScheduleDTO> list = new ArrayList<>();
        list.add(scheduleTest());
        Mockito.when(scheduleService.takeScheduleToTeacher(Mockito.any())).thenReturn(list);
        String uri = "/api/schedule-teacher/1";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ScheduleDTO[] scheduleDTOS = mapFromJson(content, ScheduleDTO[].class);
        Assertions.assertTrue(scheduleDTOS.length > 0);

    }

    @Test
    void update() throws Exception {
        Mockito.when(scheduleService.update(Mockito.any(),Mockito.any())).thenReturn(scheduleTest());
        String inputJson = mapToJson(scheduleTest());

        String uri = "/api/schedule/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        ScheduleDTO scheduleActual = mapFromJson(content, ScheduleDTO.class);
        Assertions.assertEquals(scheduleTest(), scheduleActual);

    }

    @Test
    void delete() throws Exception {
        String uri = "/api/schedule/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.readValue(json, clazz);
    }
}