package com.foxminded.dao;

import com.foxminded.schedule.Schedule;
import com.foxminded.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Schedule create(Schedule schedule) {
        String sqlInquiry = "INSERT INTO schedule (group_id,teacher_id,course_id,classroom_id,lesson_start_time,lesson_end_time) VALUES (?,?,?,?,?,?)";
        int groupId = schedule.getGroup().getGroupId();
        int teacherId = schedule.getTeacher().getTeacherId();
        int courseId = schedule.getCourse().getCourseId();
        int classroomId = schedule.getClassroom().getClassroomId();
        LocalDateTime startTime = schedule.getLessonStartTime();
        LocalDateTime endTime = schedule.getLessonEndTime();
        jdbcTemplate.update(sqlInquiry, groupId, teacherId, courseId, classroomId, startTime, endTime);
        return schedule;
    }

    @Override
    public List<Schedule> findAll() {
        String sqlInquiry = "SELECT schedule.schedule_id,classrooms.id,classrooms.number_classroom,courses.id,courses.name_course,groups.id,groups.name_group,teachers.id,teachers.first_name,teachers.last_name,schedule.lesson_start_time,schedule.lesson_end_time\n" +
                "FROM schedule schedule\n" +
                "INNER JOIN classrooms classrooms ON classrooms.id = schedule.classroom_id\n" +
                "INNER JOIN courses courses ON courses.id = schedule.course_id\n" +
                "INNER JOIN groups groups ON groups.id = schedule.group_id\n" +
                "INNER JOIN teachers teachers ON teachers.id = schedule.teacher_id";
        return jdbcTemplate.query(sqlInquiry, new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ScheduleDataBuilder scheduleDataBuilder = ScheduleDataBuilder.builder()
                        .classroomId(resultSet.getInt("classrooms.id"))
                        .classroomNumber(resultSet.getInt("classrooms.number_classroom"))
                        .courseId(resultSet.getInt("courses.id"))
                        .nameCourse(resultSet.getString("courses.name_course"))
                        .groupId(resultSet.getInt("groups.id"))
                        .groupName(resultSet.getString("groups.name_group"))
                        .teacherId(resultSet.getInt("teachers.id"))
                        .teacherFirstName(resultSet.getString("teachers.first_name"))
                        .teacherLastName(resultSet.getString("teachers.last_name"))
                        .scheduleId(resultSet.getInt("schedule.schedule_id"))
                        .timeStart(resultSet.getString("schedule.lesson_start_time"))
                        .timeEnd(resultSet.getString("schedule.lesson_end_time"))
                        .build();
                return new Schedule(scheduleDataBuilder.getGroup(), scheduleDataBuilder.getTeacher(), scheduleDataBuilder.getCourse(), scheduleDataBuilder.getClassroom(), scheduleDataBuilder.getTimeStart(), scheduleDataBuilder.getTimeEnd(), scheduleDataBuilder.getScheduleId());
            }
        });
    }

    @Override
    public List<Schedule> takeScheduleToTeacher(Teacher teacher) {
        int teacherId = teacher.getTeacherId();
        String sqlInquiry = "SELECT schedule.schedule_id,classrooms.id,classrooms.number_classroom,courses.id,courses.name_course,groups.id,groups.name_group,teachers.id,teachers.first_name,teachers.last_name,schedule.lesson_start_time,schedule.lesson_end_time\n" +
                "FROM schedule schedule\n" +
                "INNER JOIN classrooms classrooms ON classrooms.id = schedule.classroom_id\n" +
                "INNER JOIN courses courses ON courses.id = schedule.course_id\n" +
                "INNER JOIN groups groups ON groups.id = schedule.group_id\n" +
                "INNER JOIN teachers teachers ON teachers.id = schedule.teacher_id\n" +
                "WHERE schedule.teacher_id = ?";
        return jdbcTemplate.query(sqlInquiry, new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ScheduleDataBuilder scheduleDataBuilder = ScheduleDataBuilder.builder()
                        .classroomId(resultSet.getInt("classrooms.id"))
                        .classroomNumber(resultSet.getInt("classrooms.number_classroom"))
                        .courseId(resultSet.getInt("courses.id"))
                        .nameCourse(resultSet.getString("courses.name_course"))
                        .groupId(resultSet.getInt("groups.id"))
                        .groupName(resultSet.getString("groups.name_group"))
                        .teacherId(resultSet.getInt("teachers.id"))
                        .teacherFirstName(resultSet.getString("teachers.first_name"))
                        .teacherLastName(resultSet.getString("teachers.last_name"))
                        .scheduleId(resultSet.getInt("schedule.schedule_id"))
                        .timeStart(resultSet.getString("schedule.lesson_start_time"))
                        .timeEnd(resultSet.getString("schedule.lesson_end_time"))
                        .build();
                return new Schedule(scheduleDataBuilder.getGroup(), scheduleDataBuilder.getTeacher(), scheduleDataBuilder.getCourse(), scheduleDataBuilder.getClassroom(), scheduleDataBuilder.getTimeStart(), scheduleDataBuilder.getTimeEnd(), scheduleDataBuilder.getScheduleId());
            }
        }, teacherId);
    }

    @Override
    public Schedule update(Schedule scheduleNew, Schedule scheduleOld) {
        String sqlInquiry = "UPDATE schedule SET  group_id = ?,teacher_id = ?,course_id = ?,classroom_id = ?,lesson_start_time = ?,lesson_end_time = ?\n" +
                "WHERE schedule_id = ?;";
        int scheduleOldId = scheduleOld.getScheduleId();
        int groupId = scheduleNew.getGroup().getGroupId();
        int teacherId = scheduleNew.getTeacher().getTeacherId();
        int courseId = scheduleNew.getCourse().getCourseId();
        int classroomId = scheduleNew.getClassroom().getClassroomId();
        LocalDateTime startTime = scheduleNew.getLessonStartTime();
        LocalDateTime endTime = scheduleNew.getLessonEndTime();
        jdbcTemplate.update(sqlInquiry, groupId, teacherId, courseId, classroomId, startTime, endTime, scheduleOldId);
        return scheduleNew;
    }

    @Override
    public void delete(Schedule schedule) {
        int scheduleId = schedule.getScheduleId();
        String sqlInquiry = "DELETE FROM schedule WHERE schedule_id = ? ";
        jdbcTemplate.update(sqlInquiry, scheduleId);
    }

    @PostConstruct
    public void creteTable() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableSchedule.sql"));
        resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
    }
}
