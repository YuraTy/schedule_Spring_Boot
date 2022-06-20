package com.foxminded.controllers;

import com.foxminded.dto.ScheduleDTO;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.*;
import com.foxminded.violation.ValidationErrorResponse;
import com.foxminded.violation.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("schedule")
@Validated
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

    private static final String NAME_ATTRIBUTE_SCHEDULE = "scheduleDTO";
    private static final String NAME_ATTRIBUTE_TEACHER = "teacherDTO";
    private static final String NAME_ATTRIBUTE_ALL = "allSchedule";
    private static final String PAGE_UPDATE = "page-schedule-update";
    private static final String PAGE_CREATE = "page-schedule-create";
    private static final String PAGE_DELETE = "page-schedule-delete";
    private static final String PAGE_SCHEDULE_TEACHER = "schedule-to-teacher";
    private static final String MESSAGE_INFO = "message";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SAVE_ALL = "savedSuccessful";
    private boolean triggerCreate = false;

    @PostMapping("/create-schedule")
    public String create(@ModelAttribute(NAME_ATTRIBUTE_SCHEDULE) @Valid ScheduleDTO scheduleDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_CREATE;
        } else if (existenceCheckClassroom(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "There is no class with ID " + scheduleDTO.getClassroomDTO().getId());
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_CREATE;
        } else if (existenceCheckCourse(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "There is no course with ID " + scheduleDTO.getCourseDTO().getId());
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_CREATE;
        } else if (existenceCheckGroup(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "There is no group with ID " + scheduleDTO.getGroupDTO().getId());
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_CREATE;
        } else if (existenceCheckTeacher(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "There is no teacher with ID " + scheduleDTO.getTeacherDTO().getId());
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_CREATE;
        } else if (existenceCheckSchedule(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "Such a schedule already exists");
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_CREATE;
        }
        scheduleService.create(scheduleDTO);
        triggerCreate = true;
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(MESSAGE_INFO, "Added new schedule");
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return "redirect:/schedule/create-schedule";
    }

    @GetMapping("/create-schedule")
    public String getCreate(Model model) {
        if (triggerCreate) {
            model.addAttribute(SAVE_ALL, true);
            model.addAttribute(MESSAGE_INFO, "Added new schedule");
        }
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new ScheduleDTO());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        triggerCreate = false;
        return PAGE_CREATE;
    }

    @GetMapping("/all-schedule")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new ScheduleDTO());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return PAGE_CREATE;
    }

    @PostMapping("/take-schedule-to-teacher")
    public String takeScheduleToTeacher(@ModelAttribute(NAME_ATTRIBUTE_TEACHER) @Valid TeacherDTO teacherDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheckTeacher = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getTeacherDTO().getId() == teacherDTO.getId());
        if (bindingResult.hasErrors()) {
            return PAGE_SCHEDULE_TEACHER;
        } else if (!existenceCheckTeacher) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "No schedule found for this teacher");
            return PAGE_SCHEDULE_TEACHER;
        }
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        List<ScheduleDTO> scheduleList = scheduleService.takeScheduleToTeacher(teacherDTO);
        model.addAttribute("titleNameTeacher", scheduleList.get(0).getTeacherDTO());
        model.addAttribute("takeScheduleToTeacher", scheduleList);
        return PAGE_SCHEDULE_TEACHER;
    }

    @GetMapping("/take-schedule-to-teacher")
    public String getTakeScheduleToTeacher(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new ScheduleDTO());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        return PAGE_SCHEDULE_TEACHER;
    }

    @PostMapping("/update-schedule")
    public String update(@RequestParam("idOldSchedule") int idOldSchedule,
                         @ModelAttribute(NAME_ATTRIBUTE_SCHEDULE) @Valid ScheduleDTO scheduleDTO, BindingResult bindingResult,
                         Model model) {
        boolean existenceCheckIdSchedule = scheduleService.findAll().stream().anyMatch(findScheduleDTO -> findScheduleDTO.getScheduleId() == idOldSchedule);
        if (bindingResult.hasErrors()) {
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_UPDATE;
        } else if (existenceCheckClassroom(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "There is no class with ID " + scheduleDTO.getClassroomDTO().getId());
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_UPDATE;
        } else if (existenceCheckCourse(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "There is no course with ID " + scheduleDTO.getCourseDTO().getId());
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_UPDATE;
        } else if (existenceCheckGroup(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "There is no group with ID " + scheduleDTO.getGroupDTO().getId());
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_UPDATE;
        } else if (existenceCheckTeacher(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "There is no teacher with ID " + scheduleDTO.getTeacherDTO().getId());
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_UPDATE;
        } else if (existenceCheckSchedule(scheduleDTO)) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "Such a schedule already exists");
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_UPDATE;
        } else if (!existenceCheckIdSchedule) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "No ID " + idOldSchedule + " schedule found");
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_UPDATE;
        }
        scheduleService.update(scheduleDTO, new ScheduleDTO(idOldSchedule));
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(MESSAGE_INFO, "Schedule changed successfully");
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return PAGE_UPDATE;
    }

    @GetMapping("/update-schedule")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new ScheduleDTO());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping("/delete-schedule")
    public String delete(@ModelAttribute(NAME_ATTRIBUTE_SCHEDULE) @Valid ScheduleDTO scheduleDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheckIdSchedule = scheduleService.findAll().stream().anyMatch(findScheduleDTO -> findScheduleDTO.getScheduleId() == scheduleDTO.getScheduleId());
        if (bindingResult.hasErrors()) {
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_DELETE;
        } else if (!existenceCheckIdSchedule) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "No schedule with this ID " + scheduleDTO.getScheduleId() + " was found");
            model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
            return PAGE_DELETE;
        }
        scheduleService.delete(scheduleDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        model.addAttribute(MESSAGE_INFO, "Schedule with ID " + scheduleDTO.getScheduleId() + " successfully deleted");
        return PAGE_DELETE;
    }

    @GetMapping("/delete-schedule")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new ScheduleDTO());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        return PAGE_DELETE;
    }

    private boolean existenceCheckClassroom(ScheduleDTO schedule) {
        return classroomService.findAll().stream().noneMatch(classroomDTO -> classroomDTO.getId() == schedule.getClassroomDTO().getId());
    }

    private boolean existenceCheckCourse(ScheduleDTO schedule) {
        return courseService.findAll().stream().noneMatch(courseDTO -> courseDTO.getId() == (schedule.getCourseDTO().getId()));
    }

    private boolean existenceCheckGroup(ScheduleDTO schedule) {
        return groupService.findAll().stream().noneMatch(groupDTO -> groupDTO.getId() == (schedule.getGroupDTO().getId()));
    }

    private boolean existenceCheckTeacher(ScheduleDTO schedule) {
        return teacherService.findAll().stream().noneMatch(teacherDTO -> teacherDTO.getId() == schedule.getTeacherDTO().getId());
    }

    private boolean existenceCheckSchedule(ScheduleDTO schedule) {
        return scheduleService.findAll().stream().anyMatch(scheduleDTO -> (scheduleDTO.getClassroomDTO().getId() == schedule.getClassroomDTO().getId()) &&
                (scheduleDTO.getCourseDTO().getId() == schedule.getCourseDTO().getId()) &&
                (scheduleDTO.getGroupDTO().getId() == schedule.getGroupDTO().getId()) &&
                (scheduleDTO.getTeacherDTO().getId() == schedule.getTeacherDTO().getId()) &&
                (scheduleDTO.getLessonStartTime().equals(schedule.getLessonStartTime())) &&
                (scheduleDTO.getLessonEndTime().equals(schedule.getLessonEndTime())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String errorResponse(ConstraintViolationException e, Model model) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolation().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        String nameMethod = error.getViolation().get(0).getFieldName().split("\\.")[0];
        String errorMessage = error.getViolation().get(0).getMessage();
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new ScheduleDTO());
        model.addAttribute(HAS_ERRORS, true);
        model.addAttribute(MESSAGE_INFO, errorMessage);
        if (nameMethod.equals("takeScheduleToTeacher")) {
            return PAGE_SCHEDULE_TEACHER;
        }
        model.addAttribute(NAME_ATTRIBUTE_ALL, scheduleService.findAll());
        if (nameMethod.equals("create")) {
            return PAGE_CREATE;
        } else if (nameMethod.equals("delete")) {
            return PAGE_DELETE;
        }
        return PAGE_UPDATE;
    }
}
