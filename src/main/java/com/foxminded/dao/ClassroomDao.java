package com.foxminded.dao;

import com.foxminded.classroom.Classroom;

import java.util.List;

public interface ClassroomDao {

    Classroom create (Classroom classroom);
    List<Classroom> findAll();
    Classroom update (Classroom classroomNew , Classroom classroomOld);
    void delete (Classroom classroom);
}
