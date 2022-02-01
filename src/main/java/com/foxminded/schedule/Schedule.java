package com.foxminded.schedule;

import com.foxminded.classroom.Classroom;
import com.foxminded.course.Course;
import com.foxminded.group.Group;
import com.foxminded.teacher.Teacher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private Group group = new Group();
    private Teacher teacher = new Teacher();
    private Course course = new Course();
    private Classroom classroom = new Classroom();
    private LocalDateTime localDateTime;

    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, LocalDateTime localDateTime) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.localDateTime = localDateTime;
    }

    public List<Schedule> takeAllSchedule() {
        return new ArrayList<>();
    }

    public List<Schedule> takeScheduleToTeacher(Teacher teacher) {
        return new ArrayList<>();
    }
}
