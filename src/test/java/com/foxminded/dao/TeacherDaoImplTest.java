package com.foxminded.dao;

import com.foxminded.testconfig.TestConfig;
import com.foxminded.model.Teacher;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = TeacherDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

@Sql(scripts = "classpath:drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:createTableTeachers.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

class TeacherDaoImplTest {

    @Autowired
    private TeacherDaoImpl teacherDao;

    @AfterEach
    void deleteDate() {
        teacherDao.findAll()
                .forEach(p -> teacherDao.delete(p));
    }

    @Test
    void create() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov"));
        expectedList.add(new Teacher("Ivan","Sidorov"));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void findAll() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov"));
        expectedList.add(new Teacher("Ivan","Sidorov"));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void update() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        teacherDao.update(new Teacher("Georgiy","Keba"),new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov"));
        expectedList.add(new Teacher("Georgiy","Keba"));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void delete() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        teacherDao.delete(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov"));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }
}