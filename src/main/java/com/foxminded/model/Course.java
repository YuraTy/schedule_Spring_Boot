package com.foxminded.model;

import lombok.Builder;

@Builder
public class Course {

    private String nameCourse;

    private int id;

    public Course(String nameCourse, int id) {
        this.nameCourse = nameCourse;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public Course(){}

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Course))
            return false;
        Course course = (Course) obj;
        return this.nameCourse.equals(course.nameCourse) ;
    }
}
