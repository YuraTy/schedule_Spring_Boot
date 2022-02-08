package com.foxminded.dao.teacherdao;

import com.foxminded.dao.TeacherDaoImpl;
import com.foxminded.dao.testconfig.TestConfig;
import com.foxminded.teacher.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = TeacherDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
class TeacherDaoImplTest {

    @Autowired
    private TeacherDaoImpl teacherDao;


    @Test
    void create() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov"));
        expectedList.add(new Teacher("Ivan","Sidorov"));
        List<Teacher> actualList = teacherDao.findAll();
        teacherDao.delete(new Teacher("Ivan","Ivanov"));
        teacherDao.delete(new Teacher("Ivan","Sidorov"));
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
        teacherDao.delete(new Teacher("Ivan","Sidorov"));
        teacherDao.delete(new Teacher("Ivan","Ivanov"));
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
        teacherDao.delete(new Teacher("Ivan","Ivanov"));
        teacherDao.delete(new Teacher("Georgiy","Keba"));
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
        teacherDao.delete(new Teacher("Ivan","Ivanov"));
        Assertions.assertEquals(expectedList,actualList);
    }
}