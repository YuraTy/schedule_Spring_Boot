package com.foxminded.university;
import com.foxminded.classroom.Classroom;
import com.foxminded.dao.classroomdao.ClassroomDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class University implements CommandLineRunner {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public static void main(String[] args) {
        SpringApplication.run(University.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ClassroomDaoImpl classroomDao = new ClassroomDaoImpl(jdbcTemplate);

        classroomDao.create(new Classroom(5));
        System.out.println(classroomDao.findId(1));

    }
}
