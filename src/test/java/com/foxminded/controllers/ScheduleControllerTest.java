package com.foxminded.controllers;

import com.foxminded.dto.*;
import com.foxminded.model.*;
import com.foxminded.services.*;
import com.foxminded.testconfig.WebTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ScheduleController.class)
@WebMvcTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ScheduleControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private ClassroomService classroomService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private TeacherService teacherService;

    private MockMvc mockMvc;

    @BeforeEach
    void set() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void create() throws Exception {
        Assertions.assertNotNull(mockMvc);
        List<ScheduleDTO> testList = new ArrayList<>();
        testList.add(testSchedule());
        Mockito.when(scheduleService.create(Mockito.any())).thenReturn(testSchedule());
        Mockito.when(scheduleService.findAll()).thenReturn(testList);
        Mockito.when(classroomService.findAll()).thenReturn(getListClassroom());
        Mockito.when(courseService.findAll()).thenReturn(getListCourse());
        Mockito.when(groupService.findAll()).thenReturn(getListGroup());
        Mockito.when(teacherService.findAll()).thenReturn(getListTeacher());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/create-schedule")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("classroom.id","1")
                        .param("course.id","1")
                        .param("group.id","1")
                        .param("teacher.id","1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-create"));
    }

    @Test
    void getCreate() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedule/create-schedule"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-create"));
    }

    @Test
    void findAll() throws Exception {
        Assertions.assertNotNull(mockMvc);
        Mockito.when(scheduleService.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedule/all-schedule"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-create"));
    }

    @Test
    void takeScheduleToTeacher() throws Exception {
        Assertions.assertNotNull(mockMvc);
        List<ScheduleDTO> testList = new ArrayList<>();
        testList.add(testSchedule());
        Mockito.when(scheduleService.findAll()).thenReturn(testList);
        Mockito.when(scheduleService.takeScheduleToTeacher(Mockito.any())).thenReturn(testList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/take-schedule-to-teacher")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("schedule-to-teacher"));
    }

    @Test
    void getTakeScheduleToTeacher() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedule/take-schedule-to-teacher"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("schedule-to-teacher"));
    }

    @Test
    void update() throws Exception {
        assertNotNull(mockMvc);
        List<ScheduleDTO> testList = new ArrayList<>();
        testList.add(testSchedule());
        Mockito.when(classroomService.findAll()).thenReturn(getListClassroom());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/update-schedule")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("classroom.id","2")
                        .param("course.id","1")
                        .param("group.id","1")
                        .param("teacher.id","1")
                        .param("idOldSchedule","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-update"));

        Mockito.when(courseService.findAll()).thenReturn(getListCourse());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/update-schedule")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("classroom.id","1")
                        .param("course.id","2")
                        .param("group.id","1")
                        .param("teacher.id","1")
                        .param("idOldSchedule","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-update"));

        Mockito.when(groupService.findAll()).thenReturn(getListGroup());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/update-schedule")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("classroom.id","1")
                        .param("course.id","1")
                        .param("group.id","2")
                        .param("teacher.id","1")
                        .param("idOldSchedule","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-update"));

        Mockito.when(teacherService.findAll()).thenReturn(getListTeacher());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/update-schedule")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("classroom.id","1")
                        .param("course.id","1")
                        .param("group.id","1")
                        .param("teacher.id","2")
                        .param("idOldSchedule","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-update"));

        Mockito.when(scheduleService.findAll()).thenReturn(testList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/update-schedule")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("classroom.id","1")
                        .param("course.id","1")
                        .param("group.id","1")
                        .param("teacher.id","1")
                        .param("lessonStartTime", String.valueOf(LocalDateTime.parse("2016-06-22T18:10")))
                        .param("lessonEndTime", String.valueOf(LocalDateTime.parse("2016-06-22T19:10")))
                        .param("idOldSchedule","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-update"));

        Mockito.when(scheduleService.update(Mockito.any(),Mockito.any())).thenReturn(testSchedule());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/update-schedule")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("classroom.id","1")
                        .param("course.id","1")
                        .param("group.id","1")
                        .param("teacher.id","1")
                        .param("lessonStartTime", String.valueOf(LocalDateTime.parse("2016-06-22T18:10")))
                        .param("lessonEndTime", String.valueOf(LocalDateTime.parse("2016-06-22T19:20")))
                        .param("idOldSchedule","1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-update"));
    }

    @Test
    void getUpdate() throws Exception {
        assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedule/update-schedule"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-update"));
    }

    @Test
    void delete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        List<ScheduleDTO> testList = new ArrayList<>();
        testList.add(testSchedule());
        Mockito.when(scheduleService.findAll()).thenReturn(testList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/schedule/delete-schedule")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("scheduleId","2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-delete"));
    }

    @Test
    void getDelete() throws Exception {
        Assertions.assertNotNull(mockMvc);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/schedule/delete-schedule"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("page-schedule-delete"));
    }

    private List<ClassroomDTO> getListClassroom() {
        List<ClassroomDTO> testList = new ArrayList<>();
        testList.add(new ClassroomDTO(11, 1));
        return testList;
    }

    private List<CourseDTO> getListCourse() {
        List<CourseDTO> courseList = new ArrayList<>();
        courseList.add(new CourseDTO("Histori",1));
        return courseList;
    }

    private List<GroupDTO> getListGroup() {
        List<GroupDTO> groupList = new ArrayList<>();
        groupList.add(new GroupDTO("GE-22",1));
        return groupList;
    }

    private List<TeacherDTO> getListTeacher() {
        List<TeacherDTO> teacherList = new ArrayList<>();
        teacherList.add(new TeacherDTO("Yura","Odnorog",1));
        return teacherList;
    }

    private ScheduleDTO testSchedule() {
        return ScheduleDTO.builder()
                .classroom(new Classroom(2,1))
                .course(new Course("Fizika",1))
                .group(new Group("WW-11",1))
                .teacher(new Teacher("Yura","Odnorog",1))
                .scheduleId(2)
                .lessonStartTime(LocalDateTime.parse("2016-06-22T18:10"))
                .lessonEndTime(LocalDateTime.parse("2016-06-22T19:10"))
                .build();
    }
}