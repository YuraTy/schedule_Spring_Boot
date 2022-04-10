package com.foxminded.dto;

import com.foxminded.model.Classroom;
import java.util.List;

public class ClassroomDTO {
    private int numberClassroom;
    private int classroomId;

    public List<Classroom> getClassroomList() {
        return classroomList;
    }

    private List<Classroom> classroomList;

    public ClassroomDTO(Classroom classroom) {
        assert classroomList != null;
        classroomList.add(classroom);
    }

    public ClassroomDTO(int numberClassroom, int classroomId) {
        this.numberClassroom = numberClassroom;
        this.classroomId = classroomId;
    }

    public ClassroomDTO(){}

    public int getNumberClassroom() {
        return numberClassroom;
    }

    public void setNumberClassroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }
}
