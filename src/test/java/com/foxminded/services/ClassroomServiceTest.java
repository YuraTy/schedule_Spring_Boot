package com.foxminded.services;

import com.foxminded.classroom.Classroom;
import com.foxminded.dao.ClassroomDaoImpl;
import com.foxminded.dao.testconfig.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = ClassroomDaoImpl.class),
        @ContextConfiguration(classes = ClassroomService.class)
})
@ExtendWith(SpringExtension.class)
class ClassroomServiceTest {

    @Mock
    ClassroomDaoImpl classroomDao;

    @InjectMocks
    ClassroomService classroomService;

    @Test
    void create() {
        classroomService.create(new Classroom(555,1));
        Mockito.verify(classroomDao).create(Mockito.any());
    }

    @Test
    void findAll() {
        classroomService.findAll();
        Mockito.verify(classroomDao).findAll();
    }

    @Test
    void update() {
        classroomService.update(new Classroom(555,1),new Classroom(545,1));
        Mockito.verify(classroomDao).update(Mockito.any(),Mockito.any());
    }

    @Test
    void delete() {
        classroomService.delete(new Classroom(555,1));
        Mockito.verify(classroomDao).delete(Mockito.any());
    }
}