package com.foxminded.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CourseDTO {

    @NotBlank(message = "Enter the course name in detail")
    @Size(min = 3 ,max = 20, message = "Size need to be min 3 max 20")
    private String nameCourse;

    private int id;

    public CourseDTO(String nameCourse, int id) {
        this.nameCourse = nameCourse;
        this.id = id;
    }

    public CourseDTO() {}

    public CourseDTO(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
