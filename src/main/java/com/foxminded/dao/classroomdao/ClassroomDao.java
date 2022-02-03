package com.foxminded.dao.classroomdao;

import com.foxminded.classroom.Classroom;

import java.util.List;

public interface ClassroomDao {

    void create (Classroom classroom);
    List<Classroom> findAll();
    Classroom findId(int numberClassroom);
    void update (Classroom classroom , int numberClassroom);
    void delete (Classroom classroom);
}
