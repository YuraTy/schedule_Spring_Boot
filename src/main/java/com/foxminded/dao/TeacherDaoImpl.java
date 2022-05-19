package com.foxminded.dao;

import com.foxminded.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@Profile("Jdbc")
public class TeacherDaoImpl implements TeacherDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Teacher create(Teacher teacher) {
        String sqlInquiry = "INSERT INTO teachers (first_name,last_name) VALUES (?,?)";
        jdbcTemplate.update(sqlInquiry, teacher.getFirstName(), teacher.getLastName());
        return teacher;
    }

    @Override
    public List<Teacher> findAll() {
        String sqlInquiry = "SELECT first_name,last_name,id FROM teachers";
        return jdbcTemplate.query(sqlInquiry, BeanPropertyRowMapper.newInstance(Teacher.class));
    }

    @Override
    public Teacher update(Teacher teacherNew, Teacher teacherOld) {
        String sqlInquiry = "UPDATE teachers SET first_name = ?,last_name = ? WHERE first_name = ? AND last_name = ?";
        jdbcTemplate.update(sqlInquiry, teacherNew.getFirstName(), teacherNew.getLastName(), teacherOld.getFirstName(), teacherOld.getLastName());
        return teacherNew;
    }

    @Override
    public void delete(Teacher teacher) {
        String sqlInquiry = "DELETE FROM teachers WHERE first_name = ? AND last_name = ?";
        jdbcTemplate.update(sqlInquiry, teacher.getFirstName(), teacher.getLastName());
    }

    @PostConstruct
    public void creteTable() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableTeachers.sql"));
        resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
    }
}
