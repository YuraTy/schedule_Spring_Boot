package com.foxminded.dao;

import com.foxminded.course.Course;

import java.util.List;

public interface CourseDao {

    Course create(Course course);

    List<Course> findAll();

    Course update(Course courseNew, Course courseOld);

    void delete(Course course);
}
