package com.foxminded.services;

import com.foxminded.dao.TeacherDaoImpl;
import com.foxminded.objectdto.TeacherDTO;
import com.foxminded.teacher.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeacherDaoImpl teacherDao;

    public TeacherDTO create(Teacher teacher) {
        return mapping(teacherDao.create(teacher));
    }

    public List<TeacherDTO> findAll() {
        return teacherDao.findAll().stream()
                .map(p -> mapping(p))
                .collect(Collectors.toList());
    }

    public TeacherDTO update(Teacher teacherNew, Teacher teacherOld) {
        return mapping(teacherDao.update(teacherNew, teacherOld));
    }

    public TeacherDTO delete(Teacher teacher) {
        teacherDao.delete(teacher);
        return mapping(teacher);
    }

    private TeacherDTO mapping(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDTO.class);
    }
}
