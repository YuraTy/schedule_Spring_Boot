package com.foxminded.schedule;

import com.foxminded.classroom.Classroom;
import com.foxminded.course.Course;
import com.foxminded.group.Group;
import com.foxminded.teacher.Teacher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private Group group;
    private Teacher teacher;
    private Course course;
    private Classroom classroom;
    private LocalDateTime lessonStartTime;
    private LocalDateTime lessonEndTime;

    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, LocalDateTime lessonStartTime, LocalDateTime lessonEndTime) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonStartTime = lessonStartTime;
        this.lessonEndTime = lessonEndTime;
    }


    public List<Schedule> takeAllSchedule() {
        return new ArrayList<>();
    }

    public List<Schedule> takeScheduleToTeacher(Teacher teacher) {
        return new ArrayList<>();
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
}
