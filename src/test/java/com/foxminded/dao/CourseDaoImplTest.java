package com.foxminded.dao;

import com.foxminded.model.Course;
import com.foxminded.testconfig.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CourseDaoImplTest {

    @Autowired
    private CourseDaoImpl courseDao;

    @AfterEach
    void deleteDate() {
        courseDao.findAll()
                .forEach(p -> courseDao.delete(p));
    }

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
        expectedList.add(new Course("geogrfi"));
        expectedList.add(new Course("matem"));
        List<Course> actualList = courseDao.findAll();
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
        Assertions.assertEquals(expectedList, actualList);
    }
}