package com.foxminded.dao.classroomdao;

import com.foxminded.classroom.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassroomDaoImpl implements ClassroomDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ClassroomDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Classroom classroom) {
        String sqlInquiry = "INSERT INTO classroom (number_classroom) VALUES (?)";
        jdbcTemplate.update(sqlInquiry,classroom.getNumberClassroom());
    }

    @Override
    public List<Classroom> findAll() {
        String sqlInquiry = "SELECT number_classroom FROM classrooms";
        return jdbcTemplate.query(sqlInquiry,(rs,row) ->
                new Classroom(rs.getInt("number_classroom")));
    }

    @Override
    public Classroom findId(int id) {
        String sqlInquiry = "SELECT number_classroom FROM classrooms WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlInquiry,new Object[]{id},(rs,row) ->
                new Classroom(rs.getInt(id)));
    }

    @Override
    public void update(Classroom classroom, int id) {
        String sqlInquiry = "UPDATE classrooms SET number_classroom=? WHERE id = ?";
        jdbcTemplate.update(sqlInquiry,classroom.getNumberClassroom(),id);
    }

    @Override
    public void delete(Classroom classroom) {
        String sqlInquiry = "DELETE FROM classrooms WHERE number_classroom = ? ";
        jdbcTemplate.update(sqlInquiry,classroom.getNumberClassroom());
    }

    public void creteTable() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableClassroom.sql"));
        resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
    }


}
