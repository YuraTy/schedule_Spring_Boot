package com.foxminded.services;

import com.foxminded.dao.GroupDaoImpl;
import com.foxminded.group.Group;
import com.foxminded.objectdto.GroupDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private GroupDaoImpl groupDao;

    @InjectMocks
    private GroupService groupService;

    @Test
    void create() {
        groupService.create(new Group("WE-22"));
        Mockito.verify(groupDao).create(Mockito.any());
    }

    @Test
    void findAll() {
        groupService.findAll();
        Mockito.verify(groupDao).findAll();
    }

    @Test
    void update() {
        groupService.update(new Group("WE-22"),new Group("WE-23"));
        Mockito.verify(groupDao).update(Mockito.any(),Mockito.any());
    }

    @Test
    void delete() {
        groupService.delete(new Group("WE-22"));
        Mockito.verify(groupDao).delete(Mockito.any());
    }

    @Test
    void mapping() {
        Mockito.when(modelMapper.map(Mockito.any(),Mockito.any())).thenReturn(new GroupDTO());
        groupService.mapping(new Group("WE-22",2));
        Mockito.verify(modelMapper).map(Mockito.any(),Mockito.any());
    }
}