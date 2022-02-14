package com.foxminded.services;

import com.foxminded.course.Course;
import com.foxminded.dao.CourseDaoImpl;
import com.foxminded.objectdto.CourseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
        courseService.create(new Course("history"));
        Mockito.verify(courseDao).create(Mockito.any());
    }

    @Test
    void findAll() {
        courseService.findAll();
        Mockito.verify(courseDao).findAll();
    }

    @Test
    void update() {
        courseService.update(new Course("history"),new Course("space"));
        Mockito.verify(courseDao).update(Mockito.any(),Mockito.any());
    }

    @Test
    void delete() {
        courseService.delete(new Course("history"));
        Mockito.verify(courseDao).delete(Mockito.any());
    }

    @Test
    void mapping() {
        Mockito.when(modelMapper.map(Mockito.any(),Mockito.any())).thenReturn(new CourseDTO());
        courseService.mapping(new Course("FR-66",1));
        Mockito.verify(modelMapper).map(Mockito.any(),Mockito.any());
    }
}