package com.foxminded.course;
import org.springframework.stereotype.Component;

@Component
public class Course {

    private String nameCourse;

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }
}
