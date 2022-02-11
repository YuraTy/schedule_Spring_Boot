package com.foxminded.services;

import com.foxminded.course.Course;
import com.foxminded.dao.CourseDaoImpl;
import com.foxminded.dao.testconfig.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = CourseDaoImpl.class),
        @ContextConfiguration(classes = CourseService.class)
})
@ExtendWith(SpringExtension.class)
class CourseServiceTest {

    @Mock
    CourseDaoImpl courseDao;

    @InjectMocks
    CourseService courseService;

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
}