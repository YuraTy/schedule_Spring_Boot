package com.foxminded.dao;

import com.foxminded.model.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles(profiles = "Jdbc")
@DataJdbcTest
@Import(CourseDaoImpl.class)
@Sql(scripts = "classpath:drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:createTableCourses.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CourseDaoImplTest {

    @Autowired
    private CourseDao courseDao;

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
        expectedList.add(new Course("geogrfi"));
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}