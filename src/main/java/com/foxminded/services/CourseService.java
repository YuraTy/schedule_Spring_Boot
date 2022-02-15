package com.foxminded.services;

import com.foxminded.course.Course;
import com.foxminded.dao.CourseDaoImpl;
import com.foxminded.objectdto.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseDaoImpl courseDao;

    public CourseDTO create(Course course) {
        return mapping(courseDao.create(course));
    }

    public List<CourseDTO> findAll() {
        return courseDao.findAll().stream()
                .map(p -> mapping(p))
                .collect(Collectors.toList());
    }

    public CourseDTO update(Course courseNew, Course courseOld) {
        return mapping(courseDao.update(courseNew, courseOld));
    }

    public CourseDTO delete(Course course) {
        courseDao.delete(course);
        return mapping(course);
    }

    private CourseDTO mapping(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }
}
