package com.foxminded.dao.daohibernate;

import com.foxminded.model.Classroom;
import com.foxminded.testconfig.HibernateTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = HibernateTestConfig.class),
        @ContextConfiguration(classes = ClassroomDaoImplHibernate.class)
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@Transactional
class ClassroomDaoImplHibernateTest {

    @Autowired
    private ClassroomDaoImplHibernate classroomDao;

    @Test
    void create() {
        classroomDao.create(new Classroom(123));
        classroomDao.create(new Classroom(1232));
        int expectedNumber = 123;
        int actualNumber = classroomDao.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void findAll() throws SQLException {
        classroomDao.create(new Classroom(111));
        classroomDao.create(new Classroom(222));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(111,1));
        expectedList.add(new Classroom(222,2));
        List<Classroom> actualList = classroomDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        classroomDao.create(new Classroom(111));
        classroomDao.update(new Classroom(122), new Classroom(111));
        int expectedNumber = 122;
        int actualNumber = classroomDao.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void delete() {
        classroomDao.create(new Classroom(111));
        classroomDao.create(new Classroom(222));
        classroomDao.delete(new Classroom(111));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(222,2));
        List<Classroom> actualList = classroomDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}