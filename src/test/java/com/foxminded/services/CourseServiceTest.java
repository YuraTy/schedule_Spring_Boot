package com.foxminded.services;

import com.foxminded.dao.CourseDao;
import com.foxminded.dto.CourseDTO;
import com.foxminded.model.Course;
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


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CourseService.class)
@AutoConfigureMockMvc
class CourseServiceTest {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private CourseDao courseDao;

    @Autowired
    private CourseService courseService;


    @Test
    void create() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new Course("history"));
        courseService.create(new CourseDTO("history"));
        Mockito.verify(courseDao).save(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new CourseDTO("history",1));
        List<Course> testList = new ArrayList<>();
        testList.add(new Course("history", 1));
        Mockito.when(courseDao.findAll()).thenReturn(testList);
        courseService.findAll();
        Mockito.verify(courseDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new CourseDTO("history",1));
        Mockito.when(courseDao.findByNameCourse(Mockito.any())).thenReturn(new Course("history",1));
        courseService.update(new CourseDTO("history"), new CourseDTO("space"));
        Mockito.verify(courseDao).save(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        courseService.delete(new CourseDTO("history"));
        Mockito.verify(courseDao).delete(Mockito.any());
    }
}