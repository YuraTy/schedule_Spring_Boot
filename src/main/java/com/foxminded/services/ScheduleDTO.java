package com.foxminded.services;

import com.foxminded.classroom.Classroom;
import com.foxminded.course.Course;
import com.foxminded.group.Group;
import com.foxminded.teacher.Teacher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleDTO {

    private int numberClassroom;
    private String nameCourse;
    private String nameGroup;
    private String firstName;
    private String lastName;
    private int teacherId;
    private int groupId;
    private int classroomId;
    private int courseId;
    private Group group;
    private Teacher teacher;
    private Course course;
    private Classroom classroom;
    private LocalDateTime lessonStartTime;
    private LocalDateTime lessonEndTime;
    private int scheduleId;

    public int getNumberClassroom() {
        return numberClassroom;
    }

    public void setNumberClassroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public LocalDateTime getLessonStartTime() {
        return lessonStartTime;
    }

    public void setLessonStartTime(LocalDateTime lessonStartTime) {
        this.lessonStartTime = lessonStartTime;
    }

    public LocalDateTime getLessonEndTime() {
        return lessonEndTime;
    }

    public void setLessonEndTime(LocalDateTime lessonEndTime) {
        this.lessonEndTime = lessonEndTime;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
}
