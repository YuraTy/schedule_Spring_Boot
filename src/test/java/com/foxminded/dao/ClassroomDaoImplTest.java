package com.foxminded.dao;

import com.foxminded.dao.daohibernate.ClassroomDaoImplHibernate;
import com.foxminded.model.Classroom;
import com.foxminded.testconfig.TestConfig;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = ClassroomDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles(profiles = "Jdbc")
class ClassroomDaoImplTest {

    @Autowired
    private ClassroomDao classroomDaoImpl;

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableClassroom.sql"})
    })
    void create() {
        classroomDaoImpl.create(new Classroom(123,1));
        int expectedNumber = 123;
        int actualNumber = classroomDaoImpl.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableClassroom.sql"})
    })
    void findAll() throws SQLException {
        classroomDaoImpl.create(new Classroom(111));
        classroomDaoImpl.create(new Classroom(222));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(111,1));
        expectedList.add(new Classroom(222,2));
        List<Classroom> actualList = classroomDaoImpl.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableClassroom.sql"})
    })
    void update() {
        classroomDaoImpl.create(new Classroom(111,1));
        classroomDaoImpl.update(new Classroom(122,1), new Classroom(111,1));
        int expectedNumber = 122;
        int actualNumber = classroomDaoImpl.findAll().get(0).getNumberClassroom();
        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableClassroom.sql"})
    })
    void delete() {
        classroomDaoImpl.create(new Classroom(111,1));
        classroomDaoImpl.create(new Classroom(222,2));
        classroomDaoImpl.delete(new Classroom(111,1));
        List<Classroom> expectedList = new ArrayList<>();
        expectedList.add(new Classroom(222,2));
        List<Classroom> actualList = classroomDaoImpl.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}