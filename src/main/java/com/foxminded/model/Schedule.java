package com.foxminded.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Builder
public class Schedule {

    private Group group;
    private Teacher teacher;
    private Course course;
    private Classroom classroom;
    private LocalDateTime lessonStartTime;
    private LocalDateTime lessonEndTime;
    private int scheduleId;

    public Schedule() {}

    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, LocalDateTime lessonStartTime, LocalDateTime lessonEndTime, int scheduleId) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = lessonStartTime;
        this.lessonEndTime = lessonEndTime;
        this.scheduleId = scheduleId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    private final String dataPattern = "yyyy-MM-dd HH:mm:ss";

    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, String lessonStartTime, String lessonEndTime, int scheduleId) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = LocalDateTime.parse(lessonStartTime, DateTimeFormatter.ofPattern(dataPattern));
        this.lessonEndTime = LocalDateTime.parse(lessonEndTime, DateTimeFormatter.ofPattern(dataPattern));
        this.scheduleId = scheduleId;
    }
    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, String lessonStartTime, String lessonEndTime) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = LocalDateTime.parse(lessonStartTime,DateTimeFormatter.ofPattern(dataPattern));
        this.lessonEndTime = LocalDateTime.parse(lessonEndTime, DateTimeFormatter.ofPattern(dataPattern));
    }

    public Group getGroup() {
        return group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course getCourse() {
        return course;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public LocalDateTime getLessonStartTime() {
        return lessonStartTime;
    }

    public LocalDateTime getLessonEndTime() {
        return lessonEndTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Schedule))
            return false;
        Schedule schedule = (Schedule) obj;
        return this.classroom.equals(schedule.classroom) && this.course.equals(schedule.course) && this.teacher.equals(schedule.teacher) && this.group.equals(schedule.group);
    }
}
