package com.foxminded.objectdto;

public class CourseDTO {
    private String nameCourse;
    private int courseId;

    public CourseDTO(String nameCourse, int courseId) {
        this.nameCourse = nameCourse;
        this.courseId = courseId;
    }

    public CourseDTO() {}

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
