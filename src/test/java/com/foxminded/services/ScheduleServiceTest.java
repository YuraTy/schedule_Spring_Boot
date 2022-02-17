package com.foxminded.services;

import com.foxminded.model.Classroom;
import com.foxminded.model.Course;
import com.foxminded.dao.*;
import com.foxminded.model.Group;
import com.foxminded.dto.ScheduleDTO;
import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ScheduleServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ScheduleDaoImpl scheduleDao;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void create() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1));
        scheduleService.create(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.verify(scheduleDao).create(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1));
        List<Schedule> testList = new ArrayList<>();
        testList.add(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.when(scheduleDao.findAll()).thenReturn(testList);
        scheduleService.findAll();
        Mockito.verify(scheduleDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void takeScheduleToTeacher() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1));
        List<Schedule> testList = new ArrayList<>();
        testList.add(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.when(scheduleDao.takeScheduleToTeacher(Mockito.any())).thenReturn(testList);
        scheduleService.takeScheduleToTeacher(new Teacher("Ivan", "Ivanov", 1));
        Mockito.verify(scheduleDao).takeScheduleToTeacher(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ScheduleDTO(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1));
        scheduleService.update(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"), new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(13, 13), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.verify(scheduleDao).update(Mockito.any(), Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        scheduleService.delete(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.verify(scheduleDao).delete(Mockito.any());
    }
}