package com.foxminded.services;

import com.foxminded.dao.TeacherDaoImpl;
import com.foxminded.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    TeacherDaoImpl teacherDao;

    public Teacher create(Teacher teacher) {
        return teacherDao.create(teacher);
    }

    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    public Teacher update(Teacher teacherNew, Teacher teacherOld) {
        return teacherDao.update(teacherNew, teacherOld);
    }

    public void delete(Teacher teacher) {
        teacherDao.delete(teacher);
    }
}
