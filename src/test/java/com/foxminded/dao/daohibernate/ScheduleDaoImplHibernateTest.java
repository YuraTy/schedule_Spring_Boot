package com.foxminded.dao.daohibernate;

import com.foxminded.dao.*;
import com.foxminded.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("Hibernate")
@Import({ClassroomDaoImplHibernate.class, CourseDaoImplHibernate.class, GroupDaoImplHibernate.class, TeacherDaoImplHibernate.class, ScheduleDaoImplHibernate.class})
@DataJpaTest
class ScheduleDaoImplHibernateTest {

    @Autowired
    private ClassroomDao classroomDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @BeforeEach
    void addDate() {
        teacherDao.create(testTeacher);
        groupDao.create(testGroup);
        classroomDao.create(testClassroom);
        courseDao.create(testCourse);
    }

    private Group testGroup = new Group("GT-22");
    private Teacher testTeacher = new Teacher("Ivan", "Ivanov");
    private Classroom testClassroom = new Classroom(12);
    private Course testCourse = new Course("History");

    @Test
    void create() {
        scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Schedule expectedSchedule = new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 18:10:00", "2016-06-22 19:10:25");
        Schedule actualSchedule = scheduleDao.findAll().get(0);
        Assertions.assertEquals(expectedSchedule, actualSchedule);
    }

    @Test
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
    void takeScheduleToTeacher() {
        scheduleDao.create(new Schedule(testGroup, new Teacher("Ivan", "Ivanov", 1), testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25"));
        teacherDao.create(new Teacher("Vasa", "Petrov"));
        scheduleDao.create(new Schedule(testGroup, new Teacher("Vasa", "Petrov", 6), testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25"));
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25"));
        List<Schedule> actualScheduleList = scheduleDao.takeScheduleToTeacher(new Teacher("Ivan", "Ivanov", 1));
        Assertions.assertEquals(expectedScheduleList, actualScheduleList);
    }

    @Test
    void update() {
        scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        scheduleDao.update(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 17:10:00", "2016-06-22 18:10:25"), new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25", 5));
        List<Schedule> actualScheduleList = scheduleDao.findAll();
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 17:10:00", "2016-06-22 18:10:25", 1));
        Assertions.assertEquals(expectedScheduleList, actualScheduleList);
    }

    @Test
    void delete() {
        Schedule schedule0 = scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 19:10:00", "2016-06-22 18:10:25"));
        Schedule schedule1 = scheduleDao.create(new Schedule(testGroup, testTeacher, testCourse, testClassroom, "2016-06-22 14:10:00", "2016-06-22 15:10:25"));
        scheduleDao.delete(schedule0);
        List<Schedule> expectedScheduleList = new ArrayList<>();
        expectedScheduleList.add(schedule1);
        List<Schedule> actualScheduleList = scheduleDao.findAll();
        Assertions.assertEquals(expectedScheduleList, actualScheduleList);
    }
}