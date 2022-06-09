package com.foxminded.dao;

import com.foxminded.model.Teacher;
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
class TeacherDaoImplTest {

    @Autowired
    private TeacherDao teacherDao;

    @Test
    void create() {
        teacherDao.save(new Teacher("Ivan","Ivanov"));
        teacherDao.save(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov",1));
        expectedList.add(new Teacher("Ivan","Sidorov",2));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void findAll() {
        teacherDao.save(new Teacher("Ivan","Ivanov"));
        teacherDao.save(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov",1));
        expectedList.add(new Teacher("Ivan","Sidorov",2));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void update() {
        teacherDao.save(new Teacher("Ivan","Ivanov"));
        teacherDao.save(new Teacher("Ivan","Sidorov"));
        Teacher teacherBook = teacherDao.findByFirstNameAndLastName("Ivan","Sidorov");
        teacherBook.setFirstName("Georgiy");
        teacherBook.setLastName("Keba");
        teacherDao.save(teacherBook);
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov",1));
        expectedList.add(new Teacher("Georgiy","Keba",2));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void delete() {
        teacherDao.save(new Teacher("Ivan","Ivanov"));
        teacherDao.save(new Teacher("Ivan","Sidorov"));
        teacherDao.delete(new Teacher("Ivan","Sidorov",2));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov",1));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }
}