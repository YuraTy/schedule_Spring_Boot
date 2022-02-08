package com.foxminded.dao;

import com.foxminded.classroom.Classroom;
import com.foxminded.course.Course;
import com.foxminded.group.Group;
import com.foxminded.schedule.Schedule;
import com.foxminded.teacher.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapperInterface<Schedule> {
    @Override
    public Schedule mapper(ResultSet resultSet, int rowNum) throws SQLException {
        int classroomId = resultSet.getInt("classrooms.id");
        int classroomNumber = resultSet.getInt("classrooms.number_classroom");
        Classroom classroom = new Classroom(classroomNumber,classroomId);
        int courseId = resultSet.getInt("courses.id");
        String nameCourse = resultSet.getString("courses.name_course");
        Course course = new Course(nameCourse,courseId);
        int groupId = resultSet.getInt("groups.id");
        String groupName = resultSet.getString("groups.name_group");
        Group group = new Group(groupName,groupId);
        int teacherId = resultSet.getInt("teachers.id");
        String teacherFirstName = resultSet.getString("teachers.first_name");
        String teacherLastName = resultSet.getString("teachers.last_name");
        Teacher teacher = new Teacher(teacherFirstName,teacherLastName,teacherId);
        int scheduleId = resultSet.getInt("schedule.schedule_id");
        String timeStart = resultSet.getString("schedule.lesson_start_time");
        String timeEnd = resultSet.getString("schedule.lesson_end_time");
        return new Schedule(group,teacher,course,classroom,timeStart,timeEnd,scheduleId);
    }
}
