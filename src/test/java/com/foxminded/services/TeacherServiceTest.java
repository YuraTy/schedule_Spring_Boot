package com.foxminded.services;

import com.foxminded.dao.TeacherDaoImpl;
import com.foxminded.objectdto.TeacherDTO;
import com.foxminded.teacher.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TeacherDaoImpl teacherDao;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void create() {
        teacherService.create(new Teacher("Vova","Turenko"));
        Mockito.verify(teacherDao).create(Mockito.any());
    }

    @Test
    void findAll() {
        teacherService.findAll();
        Mockito.verify(teacherDao).findAll();
    }

    @Test
    void update() {
        teacherService.update(new Teacher("Vova","Turenko"),new Teacher("Ivan","Turenko"));
        Mockito.verify(teacherDao).update(Mockito.any(),Mockito.any());
    }

    @Test
    void delete() {
        teacherService.delete(new Teacher("Vova","Turenko"));
        Mockito.verify(teacherDao).delete(Mockito.any());
    }

    @Test
    void mapping() {
        Mockito.when(modelMapper.map(Mockito.any(),Mockito.any())).thenReturn(new TeacherDTO());
        teacherService.mapping(new Teacher("Vova","Turenko"));
        Mockito.verify(modelMapper).map(Mockito.any(),Mockito.any());
    }
}