package com.foxminded.dao;

import com.foxminded.model.Classroom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClassroomDaoImplTest {

    @Autowired
    private ClassroomDao classroomDao;

    @Test
    void create() {
        classroomDao.save(new Classroom(123,1));
        int expectedNumber = 123;
        int actualNumber = classroomDao.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void findAll() {
        classroomDao.save(new Classroom(111));
        classroomDao.save(new Classroom(222));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(111,1));
        expectedList.add(new Classroom(222,2));
        List<Classroom> actualList = classroomDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        classroomDao.save(new Classroom(111));
        Classroom classroomBook = classroomDao.findAll().get(0);
        classroomBook.setNumberClassroom(122);
        classroomDao.save(classroomBook);
        int expectedNumber = 122;
        int actualNumber = classroomDao.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void delete() {
        classroomDao.save(new Classroom(111));
        classroomDao.save(new Classroom(222));
        classroomDao.delete(new Classroom(111,1));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(222,2));
        List<Classroom> actualList = classroomDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}