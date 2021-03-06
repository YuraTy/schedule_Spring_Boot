package com.foxminded.services;

import com.foxminded.dao.TeacherDao;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.model.Teacher;
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

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = TeacherService.class)
@AutoConfigureMockMvc
class TeacherServiceTest {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private TeacherDao teacherDao;

    @Autowired
    private TeacherService teacherService;

    @Test
    void create() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new Teacher("Vova", "Turenko"));
        teacherService.create(new TeacherDTO("Vova", "Turenko"));
        Mockito.verify(teacherDao).save(Mockito.any());
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
        Mockito.when(teacherDao.findByFirstNameAndLastName(Mockito.any(),Mockito.any())).thenReturn(new Teacher("Vova", "Turenko", 1));
        teacherService.update(new TeacherDTO("Vova", "Turenko"), new TeacherDTO("Ivan", "Turenko"));
        Mockito.verify(teacherDao).save(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        teacherService.delete(new TeacherDTO("Vova", "Turenko"));
        Mockito.verify(teacherDao).delete(Mockito.any());
    }
}