package com.foxminded.controllers;

import com.foxminded.dto.ScheduleDTO;
import com.foxminded.model.*;
import com.foxminded.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TeacherService teacherService;

    private static final String NAME_ATTRIBUTE_SCHEDULE = "schedule";
    private static final String NAME_ATTRIBUTE_TEACHER = "teacher";
    private static final String NAME_ATTRIBUTE_ALL = "allSchedule";
    private static final String PAGE_UPDATE = "page-schedule-update";
    private static final String PAGE_CREATE = "page-schedule-create";
    private static final String PAGE_DELETE = "page-schedule-delete";
    private static final String PAGE_SCHEDULE_TEACHER = "schedule-to-teacher";
    private static final String MESSAGE_INFO = "message";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SAVE_ALL = "savedSuccessful";

    @PostMapping("/create-schedule")
    public String create(@ModelAttribute(NAME_ATTRIBUTE_SCHEDULE) Schedule schedule, Model model, BindingResult bindingResult) {
        try {
            if (!existenceCheckClassroom(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "There is no class with ID " + schedule.getClassroom().getId());
                return PAGE_CREATE;
            } else if (!existenceCheckCourse(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "There is no course with ID " + schedule.getCourse().getId());
                return PAGE_CREATE;
            } else if (!existenceCheckGroup(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "There is no group with ID " + schedule.getGroup().getId());
                return PAGE_CREATE;
            } else if (!existenceCheckTeacher(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "There is no teacher with ID " + schedule.getTeacher().getId());
                return PAGE_CREATE;
            } else if (bindingResult.hasErrors() || existenceCheckSchedule(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "Such a schedule already exists");
                return PAGE_CREATE;
            }
            scheduleService.create(schedule);
            model.addAttribute(SAVE_ALL, true);
            model.addAttribute(MESSAGE_INFO, "Added new schedule");
            return PAGE_CREATE;
        } finally {
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        }
    }

    @GetMapping("/create-schedule")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return PAGE_CREATE;
    }

    @GetMapping("/all-schedule")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return PAGE_CREATE;
    }

    @PostMapping("/take-schedule-to-teacher")
    public String takeScheduleToTeacher(@ModelAttribute(NAME_ATTRIBUTE_TEACHER) Teacher teacher, Model model, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        boolean existenceCheckTeacher = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getTeacher().getId() == teacher.getId());
        if (!existenceCheckTeacher) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "No schedule found for this teacher");
            return PAGE_SCHEDULE_TEACHER;
        }
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        List<ScheduleDTO> scheduleList = scheduleService.takeScheduleToTeacher(teacher);
        model.addAttribute("titleNameTeacher", scheduleList.get(0).getTeacher());
        model.addAttribute("takeScheduleToTeacher", scheduleList);
        return PAGE_SCHEDULE_TEACHER;
    }

    @GetMapping("/take-schedule-to-teacher")
    public String getTakeScheduleToTeacher(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        return PAGE_SCHEDULE_TEACHER;
    }

    @PostMapping("/update-schedule")
    public String update(@RequestParam("idOldSchedule") int idOldSchedule,
                         @ModelAttribute(NAME_ATTRIBUTE_SCHEDULE) Schedule schedule,
                         Model model, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        boolean existenceCheckIdSchedule = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getScheduleId() == idOldSchedule);
        try {
            if (!existenceCheckClassroom(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "There is no class with ID " + schedule.getClassroom().getId());
                return PAGE_UPDATE;
            } else if (!existenceCheckCourse(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "There is no course with ID " + schedule.getCourse().getId());
                return PAGE_UPDATE;
            } else if (!existenceCheckGroup(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "There is no group with ID " + schedule.getGroup().getId());
                return PAGE_UPDATE;
            } else if (!existenceCheckTeacher(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "There is no teacher with ID " + schedule.getTeacher().getId());
                return PAGE_UPDATE;
            } else if (bindingResult.hasErrors() || existenceCheckSchedule(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "Such a schedule already exists");
                return PAGE_UPDATE;
            }else if (!existenceCheckIdSchedule) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "No ID " + idOldSchedule + " schedule found");
                return PAGE_UPDATE;
            } else if (existenceCheckSchedule(schedule)) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "A new schedule already exists");
                return PAGE_UPDATE;
            }
            scheduleService.update(schedule, new Schedule(idOldSchedule));
            model.addAttribute(SAVE_ALL, true);
            model.addAttribute(MESSAGE_INFO, "Schedule changed successfully");
            return PAGE_UPDATE;
        } finally {
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        }
    }

    @GetMapping("/update-schedule")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping("/delete-schedule")
    public String delete(@ModelAttribute(NAME_ATTRIBUTE_SCHEDULE) Schedule schedule, Model model, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        boolean existenceCheckIdSchedule = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getScheduleId() == schedule.getScheduleId());
        if (!existenceCheckIdSchedule || bindingResult.hasErrors()) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "No schedule with this ID " + schedule.getScheduleId() + " was found");
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_DELETE;
        }
        scheduleService.delete(schedule);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        model.addAttribute(MESSAGE_INFO, "Schedule with ID " + schedule.getScheduleId() + " successfully deleted");
        return PAGE_DELETE;
    }

    @GetMapping("/delete-schedule")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return PAGE_DELETE;
    }

    private boolean existenceCheckClassroom(Schedule schedule) {
        return classroomService.findAll().stream().anyMatch(classroomDTO -> classroomDTO.getId() == schedule.getClassroom().getId());
    }

    private boolean existenceCheckCourse(Schedule schedule) {
        return courseService.findAll().stream().anyMatch(courseDTO -> courseDTO.getId() == (schedule.getCourse().getId()));
    }

    private boolean existenceCheckGroup(Schedule schedule) {
        return groupService.findAll().stream().anyMatch(groupDTO -> groupDTO.getId() == (schedule.getGroup().getId()));
    }

    private boolean existenceCheckTeacher(Schedule schedule) {
        return teacherService.findAll().stream().anyMatch(teacherDTO -> teacherDTO.getId() == schedule.getTeacher().getId());
    }

    private boolean existenceCheckSchedule(Schedule schedule) {
        return scheduleService.findAll().stream().anyMatch(scheduleDTO -> (scheduleDTO.getClassroom().getId() == schedule.getClassroom().getId()) &&
                (scheduleDTO.getCourse().getId() == schedule.getCourse().getId()) &&
                (scheduleDTO.getGroup().getId() == schedule.getGroup().getId()) &&
                (scheduleDTO.getTeacher().getId() == schedule.getTeacher().getId()) &&
                (scheduleDTO.getLessonStartTime().equals(schedule.getLessonStartTime())) &&
                (scheduleDTO.getLessonEndTime().equals(schedule.getLessonEndTime())));
    }
}
