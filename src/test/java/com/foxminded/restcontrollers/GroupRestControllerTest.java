package com.foxminded.restcontrollers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.dto.GroupDTO;
import com.foxminded.model.Group;
import com.foxminded.services.GroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class GroupRestControllerTest {

    @MockBean
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    private GroupDTO groupTest() {
        return new GroupDTO("WW-00", 1);
    }

    @Test
    void create() throws Exception {
        Mockito.when(groupService.create(Mockito.any())).thenReturn(groupTest());
        String inputJson = mapToJson(groupTest());

        String uri = "/api/group";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        GroupDTO groupActual = mapFromJson(content, GroupDTO.class);
        Assertions.assertEquals(groupTest(), groupActual);


    }

    @Test
    void findAll() throws Exception {
        List<GroupDTO> list = new ArrayList<>();
        list.add(groupTest());
        Mockito.when(groupService.findAll()).thenReturn(list);
        String uri = "/api/group";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        GroupDTO[] groupDTOS = mapFromJson(content, GroupDTO[].class);
        Assertions.assertTrue(groupDTOS.length > 0);
    }

    @Test
    void update() throws Exception {
        Mockito.when(groupService.update(Mockito.any(), Mockito.any())).thenReturn(groupTest());
        String inputJson = mapToJson(groupTest());

        String uri = "/api/group/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        GroupDTO groupActual = mapFromJson(content, GroupDTO.class);
        Assertions.assertEquals(groupTest(), groupActual);
    }

    @Test
    void delete() throws Exception {
        String uri = "/api/group/1";
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