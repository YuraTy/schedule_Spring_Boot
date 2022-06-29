package com.foxminded.restcontrollers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.dao.ClassroomDao;
import com.foxminded.dto.ClassroomDTO;
import com.foxminded.services.ClassroomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClassroomRestControllerTest {

    @MockBean
    private ClassroomService classroomService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        List<ClassroomDTO> lit = new ArrayList<>();
        lit.add(new ClassroomDTO(1, 1));
        Mockito.when(classroomService.findAll()).thenReturn(lit);
        String uri = "/api/classroom";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ClassroomDTO[] classroomDTOList = mapFromJson(content, ClassroomDTO[].class);
        Assertions.assertTrue(classroomDTOList.length > 0);
    }

    @Test
    void create() throws Exception {
        Mockito.when(classroomService.create(Mockito.any())).thenReturn(new ClassroomDTO(1, 1));
        ClassroomDTO classroomDTO = new ClassroomDTO(1, 1);
        String inputJson = mapToJson(classroomDTO);

        String uri = "/api/classroom";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        ClassroomDTO classroomDTOActual = mapFromJson(content, ClassroomDTO.class);
        Assertions.assertEquals(classroomDTOActual.getNumberClassroom(), classroomDTO.getNumberClassroom());

    }

    @Test
    void update() throws Exception {
        Mockito.when(classroomService.update(Mockito.any(), Mockito.any())).thenReturn(new ClassroomDTO(1, 1));
        ClassroomDTO classroomDTO = new ClassroomDTO(1, 1);
        String inputJson = mapToJson(classroomDTO);

        String uri = "/api/classroom/1";
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);


        ClassroomDTO classroomDTOActual = mapFromJson(content, ClassroomDTO.class);
        Assertions.assertEquals(classroomDTOActual.getNumberClassroom(), classroomDTO.getNumberClassroom());
    }

    @Test
    void delete() throws Exception {
        String uri = "/api/classroom/2";
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