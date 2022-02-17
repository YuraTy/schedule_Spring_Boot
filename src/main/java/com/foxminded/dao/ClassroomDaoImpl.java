package com.foxminded.dao;

import com.foxminded.model.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class ClassroomDaoImpl implements ClassroomDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Classroom create(Classroom classroom) {
        String sqlInquiry = "INSERT INTO classrooms (number_classroom) VALUES (?)";
        jdbcTemplate.update(sqlInquiry, classroom.getNumberClassroom());
        return classroom;
    }

    @Override
    public List<Classroom> findAll() {
        String sqlInquiry = "SELECT number_classroom,id FROM classrooms";
        return jdbcTemplate.query(sqlInquiry, BeanPropertyRowMapper.newInstance(Classroom.class));
    }

    @Override
    public Classroom update(Classroom classroomNew, Classroom classroomOld) {
        String sqlInquiry = "UPDATE classrooms SET number_classroom=? WHERE number_classroom = ?";
        jdbcTemplate.update(sqlInquiry, classroomNew.getNumberClassroom(), classroomOld.getNumberClassroom());
        return classroomNew;
    }

    @Override
    public void delete(Classroom classroom) {
        String sqlInquiry = "DELETE FROM classrooms WHERE number_classroom = ? ";
        jdbcTemplate.update(sqlInquiry, classroom.getNumberClassroom());
    }

    @PostConstruct
    public void creteTable() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableClassroom.sql"));
        resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
    }
}
