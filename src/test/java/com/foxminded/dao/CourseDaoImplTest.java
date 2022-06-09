package com.foxminded.dao;

import com.foxminded.model.Course;
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
class CourseDaoImplTest {

    @Autowired
    private CourseDao courseDao;

    @Test
    void create() {
        courseDao.save(new Course("geogrfi"));
        String expectedName = "geogrfi";
        String actualName = courseDao.findAll().get(0).getNameCourse();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void findAll() {
        courseDao.save(new Course("geogrfi"));
        courseDao.save(new Course("matem"));
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi",1));
        expectedList.add(new Course("matem",2));
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        courseDao.save(new Course("geogrfi"));
        courseDao.save(new Course("matem"));
        Course courseBook = courseDao.findByNameCourse("matem");
        courseBook.setNameCourse("astronomia");
        courseDao.save(courseBook);
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi"));
        expectedList.add(new Course("astronomia"));
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void delete() {
        courseDao.save(new Course("geogrfi"));
        courseDao.save(new Course("matem"));
        courseDao.delete(new Course("matem",2));
        List<Course> expectedList = new ArrayList<>();
        expectedList.add(new Course("geogrfi",1));
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}