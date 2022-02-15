package com.foxminded.course;

public class Course {

    private String nameCourse;

    private int courseId;

    public Course(String nameCourse, int courseId) {
        this.nameCourse = nameCourse;
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
