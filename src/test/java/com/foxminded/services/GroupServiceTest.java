package com.foxminded.services;

import com.foxminded.dao.GroupDaoImpl;
import com.foxminded.group.Group;
import com.foxminded.objectdto.GroupDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GroupDaoImpl groupDao;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    private void behaviorMock() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new GroupDTO("WE-22", 1));
    }

    @Test
    void create() {
        groupService.create(new Group("WE-22"));
        Mockito.verify(groupDao).create(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        List<Group> testList = new ArrayList<>();
        testList.add(new Group("WE-22", 1));
        Mockito.when(groupDao.findAll()).thenReturn(testList);
        groupService.findAll();
        Mockito.verify(groupDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
        groupService.update(new Group("WE-22"), new Group("WE-23"));
        Mockito.verify(groupDao).update(Mockito.any(), Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        groupService.delete(new Group("WE-22"));
        Mockito.verify(groupDao).delete(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }
}