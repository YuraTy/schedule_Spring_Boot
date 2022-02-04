package com.foxminded.dao.scheduledao;

import com.foxminded.schedule.Schedule;
import com.foxminded.teacher.Teacher;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ScheduleDaoImpl implements ScheduleDao{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Schedule create(Schedule schedule) {
        String sqlInquiry = "INSERT INTO schedule (group,teacher_first_name,teacher_last_name,course,classroom,lesson_start_time,lesson_end_time) VALUE (?,?,?,?,?,?,?)";
        String nameGroup = schedule.getGroup().getNameGroup();
        String teacherFirstName = schedule.getTeacher().getFirstName();
        String teacherLastName = schedule.getTeacher().getLastName();
        String nameCourse = schedule.getCourse().getNameCourse();
        int numberClassroom = schedule.getClassroom().getNumberClassroom();
        LocalDateTime startTime = schedule.getLessonStartTime();
        LocalDateTime endTime = schedule.getLessonEndTime();
        jdbcTemplate.update(sqlInquiry,nameGroup,teacherFirstName,teacherLastName,nameCourse,numberClassroom,startTime,endTime);
        return schedule;
    }

    @Override
    public List<Schedule> findAll() {
        String sqlInquiry = "SELECT group,teacher_first_name,teacher_last_name,course,classroom,lesson_start_time,lesson_end_time FROM schedule";
        return jdbcTemplate.query(sqlInquiry, BeanPropertyRowMapper.newInstance(Schedule.class));
    }

    @Override
    public List<Schedule> takeScheduleToTeacher(Teacher teacher) {
        String sqlInquiry = "SELECT group,teacher_first_name,teacher_last_name,course,classroom,lesson_start_time,lesson_end_time FROM schedule WHERE teacher_first_name = ?, teacher_last_name = ?";
        return jdbcTemplate.query(sqlInquiry, BeanPropertyRowMapper.newInstance(Schedule.class), teacher.getLastName(),teacher.getLastName());
    }

    @Override
    public Schedule update(Schedule scheduleNew, Schedule scheduleOld) {
        String sqlInquiry = "UPDATE schedule SET group = ?,teacher_first_name = ?,teacher_last_name = ?,course = ?,classroom = ?,lesson_start_time = ?,lesson_end_time = ? WHERE group = ?,teacher_first_name = ?,teacher_last_name = ?,course = ?,classroom = ?,lesson_start_time = ?,lesson_end_time = ?";
        jdbcTemplate.update(sqlInquiry,scheduleDataForRequestQSL(scheduleNew),scheduleDataForRequestQSL(scheduleOld));
        return scheduleNew;
    }

    @Override
    public void delete(Schedule schedule) {
        String sqlInquiry = "DELETE FROM schedule WHERE group = ?,teacher_first_name = ?,teacher_last_name = ?,course = ?,classroom = ?,lesson_start_time = ?,lesson_end_time = ? ";
        jdbcTemplate.update(sqlInquiry,scheduleDataForRequestQSL(schedule));
    }

    private String scheduleDataForRequestQSL(Schedule schedule) {
        String nameGroup = schedule.getGroup().getNameGroup();
        String teacherFirstName = schedule.getTeacher().getFirstName();
        String teacherLastName = schedule.getTeacher().getLastName();
        String nameCourse = schedule.getCourse().getNameCourse();
        int numberClassroom = schedule.getClassroom().getNumberClassroom();
        LocalDateTime startTime = schedule.getLessonStartTime();
        LocalDateTime endTime = schedule.getLessonEndTime();
        return nameGroup + "," + teacherFirstName + "," + teacherLastName + "," + nameCourse + "," + numberClassroom + "," + startTime + "," + endTime;
    }
}
