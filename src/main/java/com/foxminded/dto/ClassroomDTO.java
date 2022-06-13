package com.foxminded.dto;

public class ClassroomDTO {

    private Integer numberClassroom;
    private int id;

    public ClassroomDTO(Integer numberClassroom, int id) {
        this.numberClassroom = numberClassroom;
        this.id = id;
    }

    public ClassroomDTO(){}

    public ClassroomDTO(Integer numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public Integer getNumberClassroom() {
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
