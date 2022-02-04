package com.foxminded.dao.teacherdao;

import com.foxminded.teacher.Teacher;

import java.util.List;

public interface TeacherDao {

    Teacher create(Teacher teacher);

    List<Teacher> findAll();

    Teacher update(Teacher teacherNew, Teacher teacherOld);

    void delete(Teacher teacher);
}
