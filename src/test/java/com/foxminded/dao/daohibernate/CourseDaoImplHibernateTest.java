package com.foxminded.dao.daohibernate;

import com.foxminded.model.Course;
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

import java.util.ArrayList;
import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = HibernateTestConfig.class),
        @ContextConfiguration(classes = CourseDaoImplHibernate.class)
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@Transactional
class CourseDaoImplHibernateTest {

    @Autowired
    private CourseDaoImplHibernate courseDao;

    @Test
    void create() {
        courseDao.create(new Course("geogrfi"));
        String expectedName = "geogrfi";
        String actualName = courseDao.findAll().get(0).getNameCourse();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void findAll() {
        courseDao.create(new Course("geogrfi"));
        courseDao.create(new Course("matem"));
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi",1));
        expectedList.add(new Course("matem",2));
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        courseDao.create(new Course("geogrfi"));
        courseDao.create(new Course("matem"));
        courseDao.update(new Course("astronomia"), new Course("matem"));
        courseDao.create(new Course("history"));
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi"));
        expectedList.add(new Course("astronomia"));
        expectedList.add(new Course("history"));
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void delete() {
        courseDao.create(new Course("geogrfi"));
        courseDao.create(new Course("matem"));
        courseDao.delete(new Course("matem"));
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi",1));
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}