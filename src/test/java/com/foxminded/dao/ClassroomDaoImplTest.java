package com.foxminded.dao;

import com.foxminded.model.Classroom;
import com.foxminded.testconfig.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = ClassroomDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClassroomDaoImplTest {

    @Autowired
    ClassroomDaoImpl classroomDaoImpl;

    @AfterEach
    void deleteDate() {
        classroomDaoImpl.findAll()
                .forEach(p -> classroomDaoImpl.delete(p));
    }

    @Test
    void create() {
        classroomDaoImpl.create(new Classroom(123));
        int expectedNumber = 123;
        int actualNumber = classroomDaoImpl.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void findAll() throws SQLException {
        classroomDaoImpl.creteTable();
        classroomDaoImpl.create(new Classroom(111));
        classroomDaoImpl.create(new Classroom(222));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(111));
        expectedList.add(new Classroom(222));
        List<Classroom> actualList = classroomDaoImpl.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        classroomDaoImpl.create(new Classroom(111));
        classroomDaoImpl.update(new Classroom(122), new Classroom(111));
        int expectedNumber = 122;
        int actualNumber = classroomDaoImpl.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void delete() {
        classroomDaoImpl.create(new Classroom(111));
        classroomDaoImpl.create(new Classroom(222));
        classroomDaoImpl.delete(new Classroom(111));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(222));
        List<Classroom> actualList = classroomDaoImpl.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}