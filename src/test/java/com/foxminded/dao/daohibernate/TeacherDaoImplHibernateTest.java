package com.foxminded.dao.daohibernate;

import com.foxminded.dao.TeacherDao;
import com.foxminded.model.Teacher;
import com.foxminded.testconfig.HibernateTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextHierarchy({
        @ContextConfiguration(classes = HibernateTestConfig.class),
        @ContextConfiguration(classes = TeacherDaoImplHibernate.class)
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("Hibernate")
@Transactional
class TeacherDaoImplHibernateTest {

    @Autowired
    private TeacherDao teacherDao;

    @Test
    void create() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov",1));
        expectedList.add(new Teacher("Ivan","Sidorov",2));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void findAll() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov",1));
        expectedList.add(new Teacher("Ivan","Sidorov",2));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void update() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        teacherDao.update(new Teacher("Georgiy","Keba"),new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov",1));
        expectedList.add(new Teacher("Georgiy","Keba",2));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void delete() {
        teacherDao.create(new Teacher("Ivan","Ivanov"));
        teacherDao.create(new Teacher("Ivan","Sidorov"));
        teacherDao.delete(new Teacher("Ivan","Sidorov"));
        List<Teacher> expectedList = new ArrayList<>();
        expectedList.add(new Teacher("Ivan","Ivanov",1));
        List<Teacher> actualList = teacherDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }
}