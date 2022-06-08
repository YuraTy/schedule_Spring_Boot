package com.foxminded.model;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lessonStartTime;

    @Column(name = "lesson_end_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
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

    public void setLessonStartTime(LocalDateTime lessonStartTime) {
        this.lessonStartTime = lessonStartTime;
    }

    public void setLessonEndTime(LocalDateTime lessonEndTime) {
        this.lessonEndTime = lessonEndTime;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add("schedule id =")
                .add(Integer.toString(scheduleId))
                .add("group id =")
                .add(Integer.toString(group.getId()))
                .add("teacher id =")
                .add(Integer.toString(teacher.getId()))
                .add("course id =")
                .add(Integer.toString(course.getId()))
                .add("classroom id =")
                .add(Integer.toString(classroom.getId()))
                .add("start time =")
                .add(lessonStartTime.toString())
                .add("end time =")
                .add(lessonEndTime.toString())
                .toString();
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
