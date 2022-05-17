package com.foxminded.services;

import com.foxminded.model.Classroom;
import com.foxminded.dao.ClassroomDaoImpl;
import com.foxminded.dto.ClassroomDTO;
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

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ClassroomServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ClassroomDaoImpl classroomDao;

    @InjectMocks
    private ClassroomService classroomService;

    @Test
    void create() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ClassroomDTO(333, 1));
        classroomService.create(new Classroom(555, 1));
        Mockito.verify(classroomDao).create(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ClassroomDTO(333, 1));
        List<Classroom> testList = new ArrayList<>();
        testList.add(new Classroom(333, 1));
        Mockito.when(classroomDao.findAll()).thenReturn(testList);
        classroomService.findAll();
        Mockito.verify(classroomDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ClassroomDTO(333, 1));
        classroomService.update(new Classroom(555, 1), new Classroom(545, 1));
        Mockito.verify(classroomDao).update(Mockito.any(), Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        classroomService.delete(new Classroom(555, 1));
        Mockito.verify(classroomDao).delete(Mockito.any());
    }
}