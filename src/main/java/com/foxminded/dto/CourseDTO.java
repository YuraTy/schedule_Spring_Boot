package com.foxminded.dto;

public class CourseDTO {

    private String nameCourse;

    private int id;

    public CourseDTO(String nameCourse, int id) {
        this.nameCourse = nameCourse;
        this.id = id;
    }

    public CourseDTO() {}

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
