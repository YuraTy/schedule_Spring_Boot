package com.foxminded.dto;

import com.foxminded.model.Classroom;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ClassroomDTO {

    @Min(value = 0,message = "Value cannot be less than 0")
    @Max(value = 5000,message = "Value cannot be greater than 5000")
    @NotNull(message = "The field must not be empty and the value must be less than 2 147 483 647")
    private Integer numberClassroom;

    private int id;

    public ClassroomDTO(Integer numberClassroom, int id) {
        this.numberClassroom = numberClassroom;
        this.id = id;
    }

    public ClassroomDTO(int id) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Classroom))
            return false;
        ClassroomDTO classroomDTO = (ClassroomDTO) obj;
        return Objects.equals(this.numberClassroom, classroomDTO.numberClassroom);
    }
}
