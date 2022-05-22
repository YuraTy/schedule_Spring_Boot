package com.foxminded.dao.daohibernate;

import com.foxminded.dao.ClassroomDao;
import com.foxminded.model.Classroom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@Import(ClassroomDaoImplHibernate.class)
@DataJpaTest
@ActiveProfiles("Hibernate")
class ClassroomDaoImplHibernateTest {

    @Autowired
    private ClassroomDao classroomDao;

    @Test
    void create() {
        classroomDao.create(new Classroom(123));
        classroomDao.create(new Classroom(1232));
        int expectedNumber = 123;
        int actualNumber = classroomDao.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void findAll() {
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