package com.foxminded.services;

import com.foxminded.dao.TeacherDaoImpl;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.model.Teacher;
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
class TeacherServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TeacherDaoImpl teacherDao;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    private void behaviorMock() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new TeacherDTO("Vova", "Turenko", 1));
    }

    @Test
    void create() {
        teacherService.create(new Teacher("Vova", "Turenko"));
        Mockito.verify(teacherDao).create(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        List<Teacher> testList = new ArrayList<>();
        testList.add(new Teacher("Vova", "Turenko", 1));
        Mockito.when(teacherDao.findAll()).thenReturn(testList);
        teacherService.findAll();
        Mockito.verify(teacherDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
        teacherService.update(new Teacher("Vova", "Turenko"), new Teacher("Ivan", "Turenko"));
        Mockito.verify(teacherDao).update(Mockito.any(), Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        teacherService.delete(new Teacher("Vova", "Turenko"));
        Mockito.verify(teacherDao).delete(Mockito.any());
    }
}