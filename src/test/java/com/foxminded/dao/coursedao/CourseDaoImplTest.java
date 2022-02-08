package com.foxminded.dao.coursedao;

import com.foxminded.course.Course;
import com.foxminded.dao.CourseDaoImpl;
import com.foxminded.dao.testconfig.TestConfig;
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
        @ContextConfiguration(classes = CourseDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
class CourseDaoImplTest {

    @Autowired
    private CourseDaoImpl courseDao;

    @Test
    void create() {
        courseDao.create(new Course("geogrfi"));
        String expectedName = "geogrfi";
        String actualName = courseDao.findAll().get(0).getNameCourse();
        courseDao.delete(new Course("geogrfi"));
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void findAll() {
        courseDao.create(new Course("geogrfi"));
        courseDao.create(new Course("matem"));
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi"));
        expectedList.add(new Course("matem"));
        List<Course> actualList = courseDao.findAll();
        courseDao.delete(new Course("geogrfi"));
        courseDao.delete(new Course("matem"));
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        courseDao.create(new Course("geogrfi"));
        courseDao.create(new Course("matem"));
        courseDao.update(new Course("matem"), new Course("astronomia"));
        courseDao.create(new Course("history"));
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi"));
        expectedList.add(new Course("astronomia"));
        expectedList.add(new Course("history"));
        List<Course> actualList = courseDao.findAll();
        courseDao.delete(new Course("geogrfi"));
        courseDao.delete(new Course("astronomia"));
        courseDao.delete(new Course("history"));
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void delete() {
        courseDao.create(new Course("geogrfi"));
        courseDao.create(new Course("matem"));
        courseDao.delete(new Course("matem"));
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi"));
        List<Course> actualList = courseDao.findAll();
        courseDao.delete(new Course("geogrfi"));
        Assertions.assertEquals(expectedList, actualList);
    }
}