package com.foxminded.dto;

import com.foxminded.model.Teacher;

import javax.validation.constraints.Size;

public class TeacherDTO {

    @Size(min = 3,max = 20,message = "Name must be between 3 and 20 characters")
    private String firstName;

    @Size(min = 3,max = 20,message = "Last name must be between 3 and 20 characters")
    private String lastName;

    private int id;

    public TeacherDTO(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public TeacherDTO(int id) {
        this.id = id;
    }

    public TeacherDTO() {}

    public TeacherDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        if (!(obj instanceof TeacherDTO))
            return false;
        TeacherDTO teacherDTO = (TeacherDTO) obj;
        return this.firstName.equals(teacherDTO.firstName) && this.lastName.equals(teacherDTO.lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
