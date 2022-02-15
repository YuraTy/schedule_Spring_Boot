package com.foxminded.objectdto;

public class ClassroomDTO {
    private int numberClassroom;
    private int classroomId;

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
