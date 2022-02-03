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
    private LocalDateTime lessonDate;

    public Schedule(Group group, Teacher teacher, Course course, Classroom classroom, LocalDateTime lessonDate) {
        this.group = group;
        this.teacher = teacher;
        this.course = course;
        this.classroom = classroom;
        this.lessonDate = lessonDate;
    }

    public List<Schedule> takeAllSchedule() {
        return new ArrayList<>();
    }

    public List<Schedule> takeScheduleToTeacher(Teacher teacher) {
        return new ArrayList<>();
    }
}
