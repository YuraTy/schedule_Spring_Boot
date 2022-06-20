package com.foxminded.services;

import com.foxminded.dto.*;
import com.foxminded.model.Classroom;
import com.foxminded.model.Course;
import com.foxminded.dao.*;
import com.foxminded.model.Group;
import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.*;


@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = {ScheduleService.class,ModelMapper.class})
@AutoConfigureMockMvc
class ScheduleServiceTest {

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ScheduleDao scheduleDao;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void create() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        scheduleService.create(new ScheduleDTO(new GroupDTO("GT-23", 1), new TeacherDTO("Ivan", "Ivanov", 1), new CourseDTO("History", 1), new ClassroomDTO(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.verify(scheduleDao).save(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void findAll() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ScheduleDTO(new GroupDTO("GT-23", 1), new TeacherDTO("Ivan", "Ivanov", 1), new CourseDTO("History", 1), new ClassroomDTO(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1));
        List<Schedule> testList = new ArrayList<>();
        testList.add(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.when(modelMapper.createTypeMap(Schedule.class,ScheduleDTO.class)).thenReturn(getModelMapper().createTypeMap(Schedule.class,ScheduleDTO.class));
        Mockito.when(scheduleDao.findAll()).thenReturn(testList);
        scheduleService.findAll();
        Mockito.verify(scheduleDao).findAll();
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void takeScheduleToTeacher() {
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new ScheduleDTO(new GroupDTO("GT-23", 1), new TeacherDTO("Ivan", "Ivanov", 1), new CourseDTO("History", 1), new ClassroomDTO(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25", 1));
        List<Schedule> testList = new ArrayList<>();
        testList.add(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.when(scheduleDao.findByTeacherId(Mockito.any())).thenReturn(testList);
        Mockito.when(modelMapper.createTypeMap(Schedule.class,ScheduleDTO.class)).thenReturn(getModelMapper().createTypeMap(Schedule.class,ScheduleDTO.class));
        scheduleService.takeScheduleToTeacher(new TeacherDTO("Ivan", "Ivanov", 1));
        Mockito.verify(scheduleDao).findByTeacherId(Mockito.any());
        Mockito.verify(modelMapper).map(Mockito.any(), Mockito.any());
    }

    @Test
    void update() {
        ScheduleDTO scheduleDTO = new ScheduleDTO(new GroupDTO("GT-23", 1), new TeacherDTO("Ivan", "Ivanov", 1), new CourseDTO("History", 1), new ClassroomDTO(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25",1);
        lenient().when(modelMapper.map(scheduleDTO, Schedule.class)).thenReturn(new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25",1));
        Schedule schedule = new Schedule(new Group("GT-23", 1), new Teacher("Ivan", "Ivanov", 1), new Course("History", 1), new Classroom(13, 13), "2016-06-22 18:10:00", "2016-06-22 19:10:25",1);
        lenient().when(modelMapper.map(schedule, ScheduleDTO.class)).thenReturn(new ScheduleDTO(new GroupDTO("GT-23", 1), new TeacherDTO("Ivan", "Ivanov", 1), new CourseDTO("History", 1), new ClassroomDTO(13, 13), "2016-06-22 18:10:00", "2016-06-22 19:10:25",1));
        Mockito.when(modelMapper.createTypeMap(Schedule.class,ScheduleDTO.class)).thenReturn(getModelMapper().createTypeMap(Schedule.class,ScheduleDTO.class));
        scheduleService.update(new ScheduleDTO(new GroupDTO("GT-23", 1), new TeacherDTO("Ivan", "Ivanov", 1), new CourseDTO("History", 1), new ClassroomDTO(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25",1), new ScheduleDTO(new GroupDTO("GT-23", 1), new TeacherDTO("Ivan", "Ivanov", 1), new CourseDTO("History", 1), new ClassroomDTO(13, 13), "2016-06-22 18:10:00", "2016-06-22 19:10:25",1));
        Mockito.verify(scheduleDao).save(Mockito.any());
        Mockito.verify(modelMapper,times(2)).map(Mockito.any(), Mockito.any());
    }

    @Test
    void delete() {
        scheduleService.delete(new ScheduleDTO(new GroupDTO("GT-23", 1), new TeacherDTO("Ivan", "Ivanov", 1), new CourseDTO("History", 1), new ClassroomDTO(12, 1), "2016-06-22 18:10:00", "2016-06-22 19:10:25"));
        Mockito.verify(scheduleDao).delete(Mockito.any());
    }

    private ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}