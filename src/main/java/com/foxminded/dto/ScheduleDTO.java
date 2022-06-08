package com.foxminded.dto;

import com.foxminded.model.Classroom;
import com.foxminded.model.Course;
import com.foxminded.model.Group;
import com.foxminded.model.Teacher;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

@Builder
public class ScheduleDTO {
    private Group group;
    private Teacher teacher;
    private Course course;
    private Classroom classroom;
    private LocalDateTime lessonStartTime;
    private LocalDateTime lessonEndTime;
    private int scheduleId;

    public ScheduleDTO(Group group, Teacher teacher, Course course, Classroom classroom, LocalDateTime lessonStartTime, LocalDateTime lessonEndTime, int scheduleId) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = lessonStartTime;
        this.lessonEndTime = lessonEndTime;
        this.scheduleId = scheduleId;
    }

    public ScheduleDTO(Group group, Teacher teacher, Course course, Classroom classroom, String lessonStartTime, String lessonEndTime, int scheduleId) {
        String dataPattern = "yyyy-MM-dd HH:mm:ss";
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = LocalDateTime.parse(lessonStartTime, DateTimeFormatter.ofPattern(dataPattern));
        this.lessonEndTime = LocalDateTime.parse(lessonEndTime, DateTimeFormatter.ofPattern(dataPattern));
        this.scheduleId = scheduleId;
    }

    public ScheduleDTO() {}

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
}
