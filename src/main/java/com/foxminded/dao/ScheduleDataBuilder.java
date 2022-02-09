package com.foxminded.dao;

import com.foxminded.classroom.Classroom;
import com.foxminded.course.Course;
import com.foxminded.group.Group;
import com.foxminded.teacher.Teacher;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleDataBuilder {

    private int classroomId;
    private int classroomNumber;
    private int courseId;
    private String nameCourse;
    private int groupId;
    private String groupName;
    private int teacherId;
    private String teacherFirstName;
    private String teacherLastName;
    private int scheduleId;
    private String timeStart;
    private String timeEnd;

    public Classroom getClassroom() {
        return new Classroom(classroomNumber, classroomId);
    }

    public Course getCourse() {
        return new Course(nameCourse, courseId);
    }

    public Group getGroup() {
        return new Group(groupName, groupId);
    }

    public Teacher getTeacher() {
        return new Teacher(teacherFirstName, teacherLastName, teacherId);
    }
}
