package com.foxminded.classroom;

import com.foxminded.dao.classroomdao.ClassroomInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Classroom implements ClassroomInterface {

    private int numberClassroom;

    public Classroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public Classroom() {

    }

    @Override
    public Classroom map(ResultSet rs, int rowNum) throws SQLException {
        return new Classroom(rs.getInt("number_classroom"));
    }

    public int getNumberClassroom() {
        return numberClassroom;
    }

    public void setNumberClassroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

}
