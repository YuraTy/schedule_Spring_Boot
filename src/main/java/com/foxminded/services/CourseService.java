package com.foxminded.services;

import com.foxminded.course.Course;
import com.foxminded.dao.CourseDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDaoImpl courseDao;

    public Course create(Course course) {
        return courseDao.create(course);
    }

    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public Course update(Course courseNew, Course courseOld) {
        return courseDao.update(courseNew, courseOld);
    }

    public void delete(Course course) {
        courseDao.delete(course);
    }




    }
