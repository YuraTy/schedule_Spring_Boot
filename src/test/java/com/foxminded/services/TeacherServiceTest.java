package com.foxminded.services;

import com.foxminded.dao.TeacherDaoImpl;
import com.foxminded.dao.testconfig.TestConfig;
import com.foxminded.teacher.Teacher;
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
        @ContextConfiguration(classes = TeacherDaoImpl.class),
        @ContextConfiguration(classes = TeacherService.class)
})
@ExtendWith(SpringExtension.class)
class TeacherServiceTest {

    @Mock
    TeacherDaoImpl teacherDao;

    @InjectMocks
    TeacherService teacherService;

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
}