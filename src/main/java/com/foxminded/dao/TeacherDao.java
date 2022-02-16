package com.foxminded.dao;

import com.foxminded.model.Teacher;

import java.util.List;

public interface TeacherDao {

    Teacher create(Teacher teacher);

    List<Teacher> findAll();

    Teacher update(Teacher teacherNew, Teacher teacherOld);

    void delete(Teacher teacher);
}
