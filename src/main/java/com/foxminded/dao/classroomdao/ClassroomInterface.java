package com.foxminded.dao.classroomdao;

import com.foxminded.classroom.Classroom;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ClassroomInterface {
    Classroom map(ResultSet rs, int rowNum) throws SQLException;
}
