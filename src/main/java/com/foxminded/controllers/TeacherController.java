package com.foxminded.controllers;

import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.ScheduleService;
import com.foxminded.services.TeacherService;
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
import javax.validation.constraints.Size;

@Controller
@RequestMapping("teacher")
@Validated
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ScheduleService scheduleService;

    private static final String NAME_ATTRIBUTE_TEACHER = "teacherDTO";
    private static final String NAME_ATTRIBUTE_ALL = "allTeacher";
    private static final String PAGE_UPDATE = "page-teacher-update";
    private static final String PAGE_CREATE = "page-teacher-create";
    private static final String PAGE_DELETE = "page-teacher-delete";
    private static final String MESSAGE_INFO = "message";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SAVE_ALL = "savedSuccessful";

    @PostMapping("/create-teacher")
    public String create(@ModelAttribute(NAME_ATTRIBUTE_TEACHER) @Valid TeacherDTO teacherDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheck = teacherService.findAll().stream().anyMatch((findTeacherDTO -> findTeacherDTO.getFirstName().equals(teacherDTO.getFirstName()) && (findTeacherDTO.getLastName().equals(teacherDTO.getLastName()))));
        if (existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
            model.addAttribute(MESSAGE_INFO, "Such a teacher already exists");
            return PAGE_CREATE;
        }
        teacherService.create(teacherDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        model.addAttribute(MESSAGE_INFO, "Added teacher " + teacherDTO);
        return PAGE_CREATE;
    }

    @GetMapping("/create-teacher")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        return PAGE_CREATE;
    }

    @GetMapping("/all-teacher")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        return PAGE_CREATE;
    }

    @PostMapping("/update-teacher")
    public String update(@RequestParam(required = false, name = "firstNameNew") @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters") String firstNameNew,
                         @RequestParam(required = false, name = "lastNameNew") @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters") String lastNameNew,
                         @RequestParam(required = false, name = "firstNameOld") String firstNameOld,
                         @RequestParam(required = false, name = "lastNameOld") String lastNameOld,
                         Model model) {
        TeacherDTO teacherNew = new TeacherDTO(firstNameNew, lastNameNew);
        TeacherDTO teacherOld = new TeacherDTO(firstNameOld, lastNameOld);
        boolean existenceCheckOld = teacherService.findAll().stream().anyMatch((teacherDTO -> teacherDTO.getFirstName().equals(firstNameOld) && (teacherDTO.getLastName().equals(lastNameOld))));
        boolean existenceCheckNew = teacherService.findAll().stream().anyMatch((teacherDTO -> teacherDTO.getFirstName().equals(firstNameNew) && (teacherDTO.getLastName().equals(lastNameNew))));
        if (!existenceCheckOld) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
            model.addAttribute(MESSAGE_INFO, "Teacher with name " + teacherOld + " not found");
            return PAGE_UPDATE;
        } else if (existenceCheckNew) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
            model.addAttribute(MESSAGE_INFO, "Teacher already " + teacherNew + " exists");
            return PAGE_UPDATE;
        }
        teacherService.update(teacherNew, teacherOld);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        model.addAttribute(MESSAGE_INFO, "Teacher changed from " + teacherOld + " to " + teacherNew);
        return PAGE_UPDATE;
    }

    @GetMapping("/update-teacher")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping("/delete-teacher")
    public String delete(@ModelAttribute(NAME_ATTRIBUTE_TEACHER) @Valid TeacherDTO teacherDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheck = teacherService.findAll().stream().anyMatch((findTeacherDTO -> findTeacherDTO.getFirstName().equals(teacherDTO.getFirstName()) && (findTeacherDTO.getLastName().equals(teacherDTO.getLastName()))));
        if (!existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
            model.addAttribute(MESSAGE_INFO, "No such teacher found for deletion " + teacherDTO);
            return PAGE_DELETE;
        }

        int idTeacher = teacherService.findAll().stream().filter(p -> (p.getLastName().equals(teacherDTO.getLastName())) && (p.getFirstName().equals(teacherDTO.getFirstName()))).findFirst().get().getId();
        boolean includedInTheSchedule = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getClassroomDTO().getId() == idTeacher);

        if (includedInTheSchedule) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "Cannot delete while teacher is on schedule");
            model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
            return PAGE_DELETE;
        }
        teacherService.delete(teacherDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        model.addAttribute(MESSAGE_INFO, "Deleted teacher " + teacherDTO);
        return PAGE_DELETE;
    }

    @GetMapping("/delete-teacher")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        return PAGE_DELETE;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String errorResponse(ConstraintViolationException e, Model model) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolation().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        String nameMethod = error.getViolation().get(0).getFieldName().split("\\.")[0];
        String errorMessage = error.getViolation().get(0).getMessage();
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(HAS_ERRORS, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        model.addAttribute(MESSAGE_INFO, errorMessage);
        if (nameMethod.equals("create")) {
            return PAGE_CREATE;
        } else if (nameMethod.equals("delete")) {
            return PAGE_DELETE;
        }
        return PAGE_UPDATE;
    }
}
