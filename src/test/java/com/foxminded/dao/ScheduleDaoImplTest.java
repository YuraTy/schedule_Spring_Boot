package com.foxminded.dao;

import com.foxminded.model.Classroom;
import com.foxminded.model.Course;
import com.foxminded.testconfig.TestConfig;
import com.foxminded.model.Group;
import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = GroupDaoImpl.class),
        @ContextConfiguration(classes = TeacherDaoImpl.class),
        @ContextConfiguration(classes = CourseDaoImpl.class),
        @ContextConfiguration(classes = ClassroomDaoImpl.class),
        @ContextConfiguration(classes = ScheduleDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ScheduleDaoImplTest {

    @Autowired
    private ScheduleDaoImpl scheduleDao;

    @Autowired
    private TeacherDaoImpl teacherDao;

    @Autowired
    private GroupDaoImpl groupDao;

    @Autowired
    private ClassroomDaoImpl classroomDao;

    @Autowired
    private CourseDaoImpl courseDao;

    @BeforeAll
    void addDate() {
        teacherDao.create(testTeacher);
        groupDao.create(testGroup);
        classroomDao.create(testClassroom);
        courseDao.create(testCourse);
    }

    @AfterAll
    void delDate() {
        teacherDao.findAll().forEach(p -> teacherDao.delete(p));
        groupDao.findAll().forEach(p -> groupDao.delete(p));
        classroomDao.findAll().forEach(p -> classroomDao.delete(p));
        courseDao.findAll().forEach(p -> courseDao.delete(p));
    }

    @AfterEach
    void deleteDate() {
        scheduleDao.findAll()
                .forEach(p -> scheduleDao.delete(p));
    }

    private Group testGroup = new Group("GT-22",1);
    private Teacher testTeacher = new Teacher("Ivan","Ivanov",1);
    private Classroom testClassroom = new Classroom(12,1);
    private Course testCourse = new Course("History",1);

//
//    @Test
//    void create() throws SQLException {
//        groupDao.findAll().stream().peek(p-> System.out.println(p.getNameGroup() + " - " + p.getGroupId())).collect(Collectors.toList());
//
//        scheduleDao.create(new Schedule(new Group("GT-22",2),testTeacher,testCourse,testClassroom,"2016-06-22 18:10:00","2016-06-22 19:10:25"));
//        Schedule expectedSchedule = new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 18:10:00","2016-06-22 19:10:25");
//        Schedule actualSchedule = scheduleDao.findAll().get(0);
//        Assertions.assertEquals(expectedSchedule,actualSchedule);
//    }

//    @Test
//    void findAll() {
//        scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25"));
//        scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 14:10:00","2016-06-22 15:10:25"));
//        List<Schedule> expectedScheduleList = new ArrayList<>();
//        Schedule schedule0 = new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25",2);
//        Schedule schedule1 = new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 14:10:00","2016-06-22 15:10:25",3);
//        expectedScheduleList.add(schedule0);
//        expectedScheduleList.add(schedule1);
//        List<Schedule> actualScheduleList = scheduleDao.findAll();
//        Assertions.assertEquals(expectedScheduleList,actualScheduleList);
//    }

//    @Test
//    void takeScheduleToTeacher() {
//        groupDao.findAll().stream().peek(p-> System.out.println(p.getNameGroup() + " - " + p.getGroupId())).collect(Collectors.toList());
//        scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25"));
//        List<Schedule> expectedScheduleList = new ArrayList<>();
//        expectedScheduleList.add(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25"));
//        List<Schedule> actualScheduleList = scheduleDao.takeScheduleToTeacher(testTeacher);
//        Assertions.assertEquals(expectedScheduleList,actualScheduleList);
//    }

//    @Test
//    void update() {
//        groupDao.findAll().stream().peek(p-> System.out.println(p.getNameGroup() + " - " + p.getGroupId())).collect(Collectors.toList());
//
//        scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 18:10:00","2016-06-22 19:10:25"));
//        scheduleDao.update(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 17:10:00","2016-06-22 18:10:25"),new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25"));
//        List<Schedule> actualScheduleList = scheduleDao.takeScheduleToTeacher(testTeacher);
//        List<Schedule> expectedScheduleList = new ArrayList<>();
//        expectedScheduleList.add(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 17:10:00","2016-06-22 18:10:25"));
//        Assertions.assertEquals(expectedScheduleList,actualScheduleList);
//    }
//
//    @Test
//    void delete() {
//        groupDao.findAll().stream().peek(p-> System.out.println(p.getNameGroup() + " - " + p.getGroupId())).collect(Collectors.toList());
//
//        Schedule schedule0 = scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 19:10:00","2016-06-22 18:10:25",3));
//        Schedule schedule1 = scheduleDao.create(new Schedule(testGroup,testTeacher,testCourse,testClassroom,"2016-06-22 14:10:00","2016-06-22 15:10:25",4));
//        scheduleDao.delete(schedule0);
//        List<Schedule> expectedScheduleList = new ArrayList<>();
//        expectedScheduleList.add(schedule1);
//        List<Schedule> actualScheduleList = scheduleDao.findAll();
//        Assertions.assertEquals(expectedScheduleList,actualScheduleList);
//    }
}