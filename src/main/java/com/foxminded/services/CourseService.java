package com.foxminded.services;

import com.foxminded.model.Course;
import com.foxminded.dao.CourseDaoImpl;
import com.foxminded.dto.CourseDTO;
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

    public void delete(Course course) {
        courseDao.delete(course);
    }

    private CourseDTO mapping(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }
}
