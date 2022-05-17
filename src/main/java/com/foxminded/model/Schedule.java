package com.foxminded.model;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Entity
@Table(name = "SCHEDULE")
public class Schedule {

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Column(name = "lesson_start_time")
    private LocalDateTime lessonStartTime;

    @Column(name = "lesson_end_time")
    private LocalDateTime lessonEndTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SCHEDULE_ID")
    private int scheduleId;

    public Schedule() {
    }

    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, LocalDateTime lessonStartTime, LocalDateTime lessonEndTime, int scheduleId) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = lessonStartTime;
        this.lessonEndTime = lessonEndTime;
        this.scheduleId = scheduleId;
    }

    public Schedule(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    private static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, String lessonStartTime, String lessonEndTime, int scheduleId) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = LocalDateTime.parse(lessonStartTime, DateTimeFormatter.ofPattern(DATA_PATTERN));
        this.lessonEndTime = LocalDateTime.parse(lessonEndTime, DateTimeFormatter.ofPattern(DATA_PATTERN));
        this.scheduleId = scheduleId;
    }

    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, String lessonStartTime, String lessonEndTime) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = LocalDateTime.parse(lessonStartTime, DateTimeFormatter.ofPattern(DATA_PATTERN));
        this.lessonEndTime = LocalDateTime.parse(lessonEndTime, DateTimeFormatter.ofPattern(DATA_PATTERN));
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

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setLessonStartTime(String startTime) {
        this.lessonStartTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setLessonEndTime(String sndTime) {
        this.lessonEndTime = LocalDateTime.parse(sndTime, DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setGroup(Group group) {
        this.group = group;
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
