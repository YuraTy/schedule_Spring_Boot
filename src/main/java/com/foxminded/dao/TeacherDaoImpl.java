package com.foxminded.dao;

import com.foxminded.teacher.Teacher;
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
        String sqlInquiry = "SELECT first_name,last_name FROM teachers";
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
    public void creteTable() throws SQLException {
        DatabaseMetaData metaData = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getMetaData();
        ResultSet databases = metaData.getTables(null,null,"%",new String[]{"TABLE"});
        boolean hasDB = false;
        while (databases.next()) {
            String databaseName = databases.getString("TABLE_NAME");
            if (databaseName.equals("TEACHERS")) {
                hasDB = true;
                break;
            }
        }
        if (hasDB){
            jdbcTemplate.update("DROP TABLE teachers");
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableTeachers.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        }else{
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableTeachers.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        }
    }
}
