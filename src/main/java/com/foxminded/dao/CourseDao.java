package com.foxminded.dao;

import com.foxminded.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDao extends JpaRepository<Course,Long> {
  Course findByNameCourse (String nameCourse);
}
