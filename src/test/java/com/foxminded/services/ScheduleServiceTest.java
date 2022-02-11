package com.foxminded.services;

import com.foxminded.classroom.Classroom;
import com.foxminded.course.Course;
import com.foxminded.dao.*;
import com.foxminded.dao.testconfig.TestConfig;
import com.foxminded.group.Group;
import com.foxminded.schedule.Schedule;
import com.foxminded.teacher.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = GroupDaoImpl.class),
        @ContextConfiguration(classes = TeacherDaoImpl.class),
        @ContextConfiguration(classes = CourseDaoImpl.class),
        @ContextConfiguration(classes = ClassroomDaoImpl.class),
        @ContextConfiguration(classes = ScheduleDaoImpl.class),
        @ContextConfiguration(classes = ScheduleService.class)
})
@ExtendWith(SpringExtension.class)
class ScheduleServiceTest {

    @Mock
    ScheduleDaoImpl scheduleDao;

    @InjectMocks
    ScheduleService scheduleService;

    @Test
    void create() {
        scheduleService.create(new Schedule(new Group("GT-23",1),new Teacher("Ivan","Ivanov",1),new Course("History",1),new Classroom(12,1),"2016-06-22 18:10:00","2016-06-22 19:10:25"));
        Mockito.verify(scheduleDao).create(Mockito.any());
    }

    @Test
    void findAll() {
        scheduleService.findAll();
        Mockito.verify(scheduleDao).findAll();
    }

    @Test
    void takeScheduleToTeacher() {
        scheduleService.takeScheduleToTeacher(new Teacher("Ivan","Ivanov",1));
        Mockito.verify(scheduleDao).takeScheduleToTeacher(Mockito.any());
    }

    @Test
    void update() {
        scheduleService.update(new Schedule(new Group("GT-23",1),new Teacher("Ivan","Ivanov",1),new Course("History",1),new Classroom(12,1),"2016-06-22 18:10:00","2016-06-22 19:10:25"),new Schedule(new Group("GT-23",1),new Teacher("Ivan","Ivanov",1),new Course("History",1),new Classroom(13,13),"2016-06-22 18:10:00","2016-06-22 19:10:25"));
        Mockito.verify(scheduleDao).update(Mockito.any(),Mockito.any());
    }

    @Test
    void delete() {
        scheduleService.delete(new Schedule(new Group("GT-23",1),new Teacher("Ivan","Ivanov",1),new Course("History",1),new Classroom(12,1),"2016-06-22 18:10:00","2016-06-22 19:10:25"));
        Mockito.verify(scheduleDao).delete(Mockito.any());
    }
}