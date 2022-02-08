package com.foxminded.dao;

import com.foxminded.classroom.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class ClassroomDaoImpl implements ClassroomDao {

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Override
    public Classroom create(Classroom classroom) {
        String sqlInquiry = "INSERT INTO classrooms (number_classroom) VALUES (?)";
        jdbcTemplate.update(sqlInquiry,classroom.getNumberClassroom());
        return classroom;
    }

    @Override
    public List<Classroom> findAll() {
        String sqlInquiry = "SELECT number_classroom FROM classrooms";
        return jdbcTemplate.query(sqlInquiry,BeanPropertyRowMapper.newInstance(Classroom.class));
    }

    @Override
    public Classroom update(Classroom classroomNew, Classroom classroomOld) {
        String sqlInquiry = "UPDATE classrooms SET number_classroom=? WHERE number_classroom = ?";
        jdbcTemplate.update(sqlInquiry,classroomNew.getNumberClassroom(),classroomOld.getNumberClassroom());
        return classroomNew;
    }

    @Override
    public void delete(Classroom classroom) {
        String sqlInquiry = "DELETE FROM classrooms WHERE number_classroom = ? ";
        jdbcTemplate.update(sqlInquiry,classroom.getNumberClassroom());
    }

    @PostConstruct
    public void creteTable() throws SQLException {
        DatabaseMetaData metaData = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getMetaData();
        ResultSet databases = metaData.getTables(null,null,"%",new String[]{"TABLE"});
        boolean hasDB = false;
        while (databases.next()) {
            String databaseName = databases.getString("TABLE_NAME");
            if (databaseName.equals("CLASSROOMS")) {
                hasDB = true;
                break;
            }
        }
        if (hasDB){
            jdbcTemplate.update("DROP TABLE classrooms");
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableClassroom.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        }else{
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableClassroom.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        }
    }
}
