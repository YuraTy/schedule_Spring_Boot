package com.foxminded.services;

import com.foxminded.dto.CourseDTO;
import com.foxminded.model.Course;
import com.foxminded.dao.CourseDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CourseDaoImpl courseDao;

    @InjectMocks
    private CourseService courseService;


    @Test
    void create() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new CourseDTO("history",1));
        courseService.create(new Course("history"));
        Mockito.verify(courseDao).create(Mockito.any());
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
        courseService.update(new Course("history"), new Course("space"));
        Mockito.verify(courseDao).update(Mockito.any(), Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        courseService.delete(new Course("history"));
        Mockito.verify(courseDao).delete(Mockito.any());
    }
}