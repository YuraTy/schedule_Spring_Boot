package com.foxminded.controllers;


import com.foxminded.dto.CourseDTO;
import com.foxminded.services.CourseService;
import com.foxminded.services.ScheduleService;
import com.foxminded.violation.ValidationErrorResponse;
import com.foxminded.violation.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.Size;


@Controller
@RequestMapping(value = "course")
@Validated
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScheduleService scheduleService;

    private static final String NAME_ATTRIBUTE_COURSE = "courseDTO";
    private static final String NAME_ATTRIBUTE_ALL = "allCourses";
    private static final String PAGE_UPDATE = "page-course-update";
    private static final String PAGE_CREATE = "page-course-create";
    private static final String PAGE_DELETE = "page-course-delete";
    private static final String MESSAGE_INFO = "message";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SAVE_ALL = "savedSuccessful";

    @PostMapping(value = "/create-course")
    public String create(@ModelAttribute(NAME_ATTRIBUTE_COURSE) @Valid CourseDTO courseDTO, BindingResult bindingResult, Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        boolean existenceCheck = courseService.findAll().stream().anyMatch(findCourseDTO -> findCourseDTO.getNameCourse().equals(courseDTO.getNameCourse()));
        if (existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "Such a course already exists");
            return PAGE_CREATE;
        }
        courseService.create(courseDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        model.addAttribute(MESSAGE_INFO, "Added course " + courseDTO.getNameCourse());
        return PAGE_CREATE;
    }

    @GetMapping(value = "/create-course")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        return PAGE_CREATE;
    }

    @GetMapping(value = "/all-course")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        return PAGE_CREATE;
    }

    @PostMapping("/update-course")
    public String update(@RequestParam(required = false, name = "courseNew") @Size(min = 3, max = 20, message = "Title must be between 3 and 20 characters") String courseNew,
                         @RequestParam(required = false, name = "courseOld") String courseOld,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        boolean existenceCheckOld = courseService.findAll().stream().anyMatch(courseDTO -> courseDTO.getNameCourse().equals(courseOld));
        boolean existenceCheckNew = courseService.findAll().stream().anyMatch(courseDTO -> courseDTO.getNameCourse().equals(courseNew));
        if (!existenceCheckOld) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "Course with name " + courseOld + " not found");
            return PAGE_UPDATE;
        } else if (existenceCheckNew) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "Course already " + courseNew + " exists");
            return PAGE_UPDATE;
        }
        courseService.update(new CourseDTO(courseNew), new CourseDTO(courseOld));
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        model.addAttribute(MESSAGE_INFO, "Course changed from " + courseOld + " to " + courseNew);
        return PAGE_UPDATE;
    }

    @GetMapping("/update-course")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping("/delete-course")
    public String delete(@ModelAttribute(NAME_ATTRIBUTE_COURSE) @Valid CourseDTO courseDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheck = courseService.findAll().stream().anyMatch(findCourseDTO -> findCourseDTO.getNameCourse().equals(courseDTO.getNameCourse()));
        if (!existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "No such course found for deletion " + courseDTO.getNameCourse());
            return PAGE_DELETE;
        }

        int idCourse = courseService.findAll().stream().filter(p -> p.getNameCourse().equals(courseDTO.getNameCourse())).findFirst().get().getId();
        boolean includedInTheSchedule = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getCourseDTO().getId() == idCourse);

        if (includedInTheSchedule) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "Cannot delete while course is on schedule");
            return PAGE_DELETE;
        }
        courseService.delete(courseDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        model.addAttribute(MESSAGE_INFO, "Deleted course " + courseDTO.getNameCourse());
        return PAGE_DELETE;
    }

    @GetMapping("/delete-course")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        return PAGE_DELETE;
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
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(HAS_ERRORS, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        model.addAttribute(MESSAGE_INFO, errorMessage);
        if (nameMethod.equals("create")) {
            return PAGE_CREATE;
        } else if (nameMethod.equals("delete")) {
            return PAGE_DELETE;
        }
        return PAGE_UPDATE;
    }
}
