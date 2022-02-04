package com.foxminded.university;

import com.foxminded.dao.classroomdao.ClassroomDaoImpl;
import com.foxminded.dbconfig.DBConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@ComponentScan("com.foxminded.university")
@Component
public class University{

    public static void main(String[] args) throws Exception {

    }

}

