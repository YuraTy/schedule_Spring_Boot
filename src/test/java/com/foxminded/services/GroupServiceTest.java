package com.foxminded.services;

import com.foxminded.dao.GroupDao;
import com.foxminded.dto.GroupDTO;
import com.foxminded.model.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;


@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = GroupService.class)
@AutoConfigureMockMvc
class GroupServiceTest {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private GroupDao groupDao;

    @Autowired
    private GroupService groupService;


    @Test
    void create() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new GroupDTO("WE-22",1));
        groupService.create(new Group("WE-22"));
        Mockito.verify(groupDao).create(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new GroupDTO("WE-22",1));
        List<Group> testList = new ArrayList<>();
        testList.add(new Group("WE-22", 1));
        Mockito.when(groupDao.findAll()).thenReturn(testList);
        groupService.findAll();
        Mockito.verify(groupDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new GroupDTO("WE-22",1));
        groupService.update(new Group("WE-22"), new Group("WE-23"));
        Mockito.verify(groupDao).update(Mockito.any(), Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        groupService.delete(new Group("WE-22"));
        Mockito.verify(groupDao).delete(Mockito.any());
    }
}