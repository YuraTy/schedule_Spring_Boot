package com.foxminded.dao;

import com.foxminded.model.Classroom;
import com.foxminded.model.Course;
import com.foxminded.model.Group;
import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@DataJdbcTest
@Import({GroupDaoImpl.class,TeacherDaoImpl.class,CourseDaoImpl.class,ClassroomDaoImpl.class,ScheduleDaoImpl.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("Jdbc")
class ScheduleDaoImplTest {

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private ClassroomDao classroomDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @BeforeEach
    void addDate() {
        teacherDao.create(testTeacher);
        groupDao.create(testGroup);
        classroomDao.create(testClassroom);
        courseDao.create(testCourse);
    }

    private Group testGroup = new Group("GT-22", 1);
    private Teacher testTeacher = new Teacher("Ivan", "Ivanov", 1);
    private Classroom testClassroom = new Classroom(12, 1);
    private Course testCourse = new Course("History", 1);

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql", "classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void create() throws SQLException {
        scheduleDao.create(new Schedule(new Group("GT-22", 1), testTeacher, testCourse, testClassroom, "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Schedule expectedSchedule = new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 18:10:00", "2016-06-22 19:10:25");
        Schedule actualSchedule = scheduleDao.findAll().get(0);
        Assertions.assertEquals(expectedSchedule, actualSchedule);
    }

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql", "classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void findAll() {
        scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25"));
        scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 14:10:00", "2016-06-22 15:10:25"));
        List<Schedule> expectedScheduleList = new ArrayList<>();
        Schedule schedule0 = new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25", 2);
        Schedule schedule1 = new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 14:10:00", "2016-06-22 15:10:25", 3);
        expectedScheduleList.add(schedule0);
        expectedScheduleList.add(schedule1);
        List<Schedule> actualScheduleList = scheduleDao.findAll();
        Assertions.assertEquals(expectedScheduleList, actualScheduleList);
    }

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql", "classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void takeScheduleToTeacher() {
        scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25"));
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25"));
        List<Schedule> actualScheduleList = scheduleDao.takeScheduleToTeacher(testTeacher);
        Assertions.assertEquals(expectedScheduleList, actualScheduleList);
    }

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql", "classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void update() {
        scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        scheduleDao.update(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 17:10:00", "2016-06-22 18:10:25"), new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25"));
        List<Schedule> actualScheduleList = scheduleDao.takeScheduleToTeacher(testTeacher);
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 17:10:00", "2016-06-22 18:10:25"));
        Assertions.assertEquals(expectedScheduleList, actualScheduleList);
    }

    @Test
    @SqlGroup({@Sql(scripts = "classpath:drop_all.sql"),
            @Sql(scripts = {"classpath:createTableGroups.sql", "classpath:createTableClassroom.sql", "classpath:createTableCourses.sql", "classpath:createTableTeachers.sql", "classpath:createTableSchedule.sql"})
    })
    void delete() {
        Schedule schedule0 = scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25", 1));
        Schedule schedule1 = scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 14:10:00", "2016-06-22 15:10:25", 2));
        scheduleDao.delete(schedule0);
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(schedule1);
        List<Schedule> actualScheduleList = scheduleDao.findAll();
        Assertions.assertEquals(expectedScheduleList, actualScheduleList);
    }
}