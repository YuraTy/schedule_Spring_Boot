package com.foxminded.services;

import com.foxminded.classroom.Classroom;
import com.foxminded.dao.ClassroomDaoImpl;
import com.foxminded.objectdto.ClassroomDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ClassroomDaoImpl classroomDao;

    @InjectMocks
    private ClassroomService classroomService;

    @Test
    void mapping() {
        Mockito.when(modelMapper.map(Mockito.any(),Mockito.any())).thenReturn(new ClassroomDTO());
        classroomService.mapping(new Classroom(555,1));
        Mockito.verify(modelMapper).map(Mockito.any(),Mockito.any());
    }

    @Test
    void create() {
        classroomService.create(new Classroom(555,1));
        Mockito.verify(classroomDao).create(Mockito.any());
    }

    @Test
    void findAll() {
        classroomService.findAll();
        Mockito.verify(classroomDao).findAll();
    }

    @Test
    void update() {
        classroomService.update(new Classroom(555,1),new Classroom(545,1));
        Mockito.verify(classroomDao).update(Mockito.any(),Mockito.any());
    }

    @Test
    void delete() {
        classroomService.delete(new Classroom(555,1));
        Mockito.verify(classroomDao).delete(Mockito.any());
    }
}