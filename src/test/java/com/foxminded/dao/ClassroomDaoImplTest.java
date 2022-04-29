package com.foxminded.dao;

import com.foxminded.model.Classroom;
import com.foxminded.testconfig.TestConfig;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts = "classpath:drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:createTableClassroom.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
        classroomDaoImpl.create(new Classroom(123,1));
        int expectedNumber = 123;
        int actualNumber = classroomDaoImpl.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void findAll() throws SQLException {
        classroomDaoImpl.create(new Classroom(111));
        classroomDaoImpl.create(new Classroom(222));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(111,1));
        expectedList.add(new Classroom(222,2));
        List<Classroom> actualList = classroomDaoImpl.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        classroomDaoImpl.create(new Classroom(111,1));
        classroomDaoImpl.update(new Classroom(122,1), new Classroom(111,1));
        int expectedNumber = 122;
        int actualNumber = classroomDaoImpl.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void delete() {
        classroomDaoImpl.create(new Classroom(111,1));
        classroomDaoImpl.create(new Classroom(222,2));
        classroomDaoImpl.delete(new Classroom(111,1));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(222,2));
        List<Classroom> actualList = classroomDaoImpl.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}