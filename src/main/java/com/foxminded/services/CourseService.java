package com.foxminded.services;

import com.foxminded.course.Course;
import com.foxminded.dao.CourseDaoImpl;
import com.foxminded.objectdto.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private ModelMapper modelMapper;

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

    public CourseDTO mapping(Course course) {
        return modelMapper.map(course,CourseDTO.class);
    }
    }
