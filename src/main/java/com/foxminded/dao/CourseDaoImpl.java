package com.foxminded.dao;

import com.foxminded.course.Course;
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
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Override
    public Course create(Course course) {
        String sqlInquiry = "INSERT INTO courses (name_course) VALUES (?)";
        jdbcTemplate.update(sqlInquiry, course.getNameCourse());
        return course;
    }

    @Override
    public List<Course> findAll() {
        String sqlInquiry = "SELECT name_course FROM courses";
        return jdbcTemplate.query(sqlInquiry, BeanPropertyRowMapper.newInstance(Course.class));
    }

    @Override
    public Course update(Course courseNew, Course courseOld) {
        String sqlInquiry = "UPDATE courses SET name_course = ? WHERE name_course = ?";
        jdbcTemplate.update(sqlInquiry, courseOld.getNameCourse(), courseNew.getNameCourse());
        return courseNew;
    }

    @Override
    public void delete(Course course) {
        String sqlInquiry = "DELETE FROM courses WHERE name_course = ?";
        jdbcTemplate.update(sqlInquiry, course.getNameCourse());
    }

    @PostConstruct
    public void creteTable() throws SQLException {
        DatabaseMetaData metaData = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getMetaData();
        ResultSet databases = metaData.getTables(null,null,"%",new String[]{"TABLE"});
        boolean hasDB = false;
        while (databases.next()) {
            String databaseName = databases.getString("TABLE_NAME");
            if (databaseName.equals("COURSES")) {
                hasDB = true;
                break;
            }
        }
        if (hasDB){
            jdbcTemplate.update("DROP TABLE courses");
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableCourses.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        }else{
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("createTableCourses.sql"));
            resourceDatabasePopulator.execute(jdbcTemplate.getDataSource());
        }
    }
}
