package com.foxminded.services;

import com.foxminded.model.Course;
import com.foxminded.dao.CourseDaoImpl;
import com.foxminded.dto.CourseDTO;
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
class CourseServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CourseDaoImpl courseDao;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    private void behaviorMock() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new CourseDTO("history", 1));
    }

    @Test
    void create() {
        courseService.create(new Course("history"));
        Mockito.verify(courseDao).create(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        List<Course> testList = new ArrayList<>();
        testList.add(new Course("history", 1));
        Mockito.when(courseDao.findAll()).thenReturn(testList);
        courseService.findAll();
        Mockito.verify(courseDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
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