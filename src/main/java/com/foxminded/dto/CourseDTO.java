package com.foxminded.dto;

import com.foxminded.model.Course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CourseDTO {

    @NotBlank(message = "The field cannot be empty")
    @Size(min = 3 ,max = 20, message = "Title must be between 3 and 20 characters")
    private String nameCourse;

    private int id;

    public CourseDTO(String nameCourse, int id) {
        this.nameCourse = nameCourse;
        this.id = id;
    }

    public CourseDTO(int id) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof CourseDTO))
            return false;
        CourseDTO courseDTO = (CourseDTO) obj;
        return this.nameCourse.equals(courseDTO.nameCourse) ;
    }
}
