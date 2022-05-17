package com.foxminded.dao;

import com.foxminded.model.Classroom;
import com.foxminded.model.Course;
import com.foxminded.testconfig.TestConfig;
import com.foxminded.model.Group;
import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = GroupDaoImpl.class),
        @ContextConfiguration(classes = TeacherDaoImpl.class),
        @ContextConfiguration(classes = CourseDaoImpl.class),
        @ContextConfiguration(classes = ClassroomDaoImpl.class),
        @ContextConfiguration(classes = ScheduleDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ScheduleDaoImplTest {

    @Autowired
    private TeacherDaoImpl teacherDao;

    @Autowired
    private GroupDaoImpl groupDao;

    @Autowired
    private ClassroomDaoImpl classroomDao;

    @Autowired
    private CourseDaoImpl courseDao;

    @Autowired
    private ScheduleDaoImpl scheduleDao;

    @BeforeEach
    void addDate() {
        teacherDao.create(testTeacher);
        groupDao.create(testGroup);
        classroomDao.create(testClassroom);
        courseDao.create(testCourse);
    }

    private Group testGroup = new Group("GT-22",1);
    private Teacher testTeacher = new Teacher("Ivan","Ivanov",1);
    private Classroom testClassroom = new Classroom(12,1);
    private Course testCourse = new Course("History",1);

    @Test
    @SqlGroup({ @Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql","classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void create() throws SQLException {
        scheduleDao.create(new Schedule(new Group("GT-22",1),testTeacher,testCourse,testClassroom,"2016-06-22 18:10:00","2016-06-22 19:10:25"));
        Schedule expectedSchedule = new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 18:10:00","2016-06-22 19:10:25");
        Schedule actualSchedule = scheduleDao.findAll().get(0);
        Assertions.assertEquals(expectedSchedule,actualSchedule);
    }

    @Test
    @SqlGroup({ @Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql","classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void findAll() {
        scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25"));
        scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 14:10:00","2016-06-22 15:10:25"));
        List<Schedule> expectedScheduleList = new ArrayList<>();
        Schedule schedule0 = new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25",2);
        Schedule schedule1 = new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 14:10:00","2016-06-22 15:10:25",3);
        expectedScheduleList.add(schedule0);
        expectedScheduleList.add(schedule1);
        List<Schedule> actualScheduleList = scheduleDao.findAll();
        Assertions.assertEquals(expectedScheduleList,actualScheduleList);
    }
    @Test
    @SqlGroup({ @Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql","classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void takeScheduleToTeacher() {
        teacherDao.create(testTeacher);
        groupDao.create(testGroup);
        classroomDao.create(testClassroom);
        courseDao.create(testCourse);
        scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25"));
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25"));
        List<Schedule> actualScheduleList = scheduleDao.takeScheduleToTeacher(testTeacher);
        Assertions.assertEquals(expectedScheduleList,actualScheduleList);
    }

    @Test
    @SqlGroup({ @Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql","classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void update() {
        scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 18:10:00","2016-06-22 19:10:25"));
        scheduleDao.update(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 17:10:00","2016-06-22 18:10:25"),new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25"));
        List<Schedule> actualScheduleList = scheduleDao.takeScheduleToTeacher(testTeacher);
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 17:10:00","2016-06-22 18:10:25"));
        Assertions.assertEquals(expectedScheduleList,actualScheduleList);
    }

    @Test
    @SqlGroup({ @Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql","classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void delete() {
        Schedule schedule0 = scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25",1));
        Schedule schedule1 = scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 14:10:00","2016-06-22 15:10:25",2));
        scheduleDao.delete(schedule0);
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(schedule1);
        List<Schedule> actualScheduleList = scheduleDao.findAll();
        Assertions.assertEquals(expectedScheduleList,actualScheduleList);
    }
}