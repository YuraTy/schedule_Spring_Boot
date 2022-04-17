package com.foxminded.dto;

public class ClassroomDTO {

    private int numberClassroom;
    private int id;

    public ClassroomDTO(int numberClassroom, int id) {
        this.numberClassroom = numberClassroom;
        this.id = id;
    }

    public ClassroomDTO(){}

    public int getNumberClassroom() {
        return numberClassroom;
    }

    public void setNumberClassroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
