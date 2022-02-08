package com.foxminded.dao;

import com.foxminded.classroom.Classroom;
import com.foxminded.course.Course;
import com.foxminded.group.Group;
import com.foxminded.schedule.Schedule;
import com.foxminded.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    public void creteTable() throws SQLException {
        DatabaseMetaData metaData = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getMetaData();
        ResultSet databases = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        boolean hasDB = false;
        while (databases.next()) {
            String databaseName = databases.getString("TABLE_NAME");
            if (databaseName.equals("SCHEDULE")) {
                hasDB = true;
                break;
            }
        }
        if (hasDB) {
            jdbcTemplate.update("DROP TABLE schedule");
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableSchedule.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        } else {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableSchedule.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        }
    }
}
