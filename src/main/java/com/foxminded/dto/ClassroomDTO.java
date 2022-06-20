package com.foxminded.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ClassroomDTO {

    @NotNull(message = "The field cannot be empty")
    @Min(value = 0,message = "Value cannot be less than 0")
    @Max(value = 5000,message = "Value cannot be greater than 5000")
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
