package com.foxminded.model;

import lombok.Builder;

import javax.persistence.Entity;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "COURSES")
@SqlResultSetMapping(name = "mappingCourse",entities = @EntityResult(entityClass = Course.class))
@NamedNativeQuery(name = "selectCourseByName", query = "SELECT id, name_course FROM courses where name_course = ?1", resultSetMapping = "mappingCourse")
public class Course {

    @Column(name = "NAME_COURSE")
    private String nameCourse;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
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
