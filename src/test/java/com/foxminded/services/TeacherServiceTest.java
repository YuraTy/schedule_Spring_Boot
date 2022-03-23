package com.foxminded.services;

import com.foxminded.dao.TeacherDaoImpl;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.model.Teacher;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TeacherServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TeacherDaoImpl teacherDao;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void create() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new TeacherDTO("Vova", "Turenko", 1));
        teacherService.create(new Teacher("Vova", "Turenko"));
        Mockito.verify(teacherDao).create(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new TeacherDTO("Vova", "Turenko", 1));
        List<Teacher> testList = new ArrayList<>();
        testList.add(new Teacher("Vova", "Turenko", 1));
        Mockito.when(teacherDao.findAll()).thenReturn(testList);
        teacherService.findAll();
        Mockito.verify(teacherDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new TeacherDTO("Vova", "Turenko", 1));
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