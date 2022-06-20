package com.foxminded.controllers;

import com.foxminded.dto.ClassroomDTO;
import com.foxminded.services.ClassroomService;
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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Controller
@RequestMapping(value = "classroom")
@Validated
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ScheduleService scheduleService;

    private static final String NAME_ATTRIBUTE_CLASSROOM = "classroomDTO";
    private static final String NAME_ATTRIBUTE_ALL = "allClassrooms";
    private static final String PAGE_UPDATE = "page-classroom-update";
    private static final String PAGE_CREATE = "page-classroom-create";
    private static final String PAGE_DELETE = "page-classroom-delete";
    private static final String MESSAGE_INFO = "message";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SAVE_ALL = "savedSuccessful";

    @PostMapping(value = "/create-classroom")
    public String create(@ModelAttribute(NAME_ATTRIBUTE_CLASSROOM) @Valid ClassroomDTO classroomDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheck = classroomService.findAll().stream().anyMatch(findClassroomDTO -> findClassroomDTO.getNumberClassroom() == classroomDTO.getNumberClassroom());
         if (existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
            model.addAttribute(MESSAGE_INFO, "Such a class already exists");
            return PAGE_CREATE;
        }
        model.addAttribute(SAVE_ALL, true);
        classroomService.create(classroomDTO);
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        model.addAttribute(MESSAGE_INFO, "Added number classroom  " + classroomDTO.getNumberClassroom());
        return PAGE_CREATE;
    }

    @GetMapping(value = "/create-classroom")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new ClassroomDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        return PAGE_CREATE;
    }

    @RequestMapping("/all-classroom")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new ClassroomDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        return PAGE_CREATE;
    }

    @PostMapping("/update-classroom")
    public String update(@RequestParam(required = false, name = "numberNew") @Min(value = 0, message = "Value cannot be less than 0")
                         @Max(value = 5000, message = "Value cannot be greater than 5000") Integer numberNew,
                         @RequestParam(required = false, name = "numberOld") Integer numberOld,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new ClassroomDTO());
        boolean existenceCheckOld = classroomService.findAll().stream().anyMatch(classroomDTO -> classroomDTO.getNumberClassroom() == numberOld);
        boolean existenceCheckNew = classroomService.findAll().stream().anyMatch(classroomDTO -> classroomDTO.getNumberClassroom() == numberNew);
        if (!existenceCheckOld) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
            model.addAttribute(MESSAGE_INFO, "Class with number " + numberOld + " not found");
            return PAGE_UPDATE;
        } else if (existenceCheckNew) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
            model.addAttribute(MESSAGE_INFO, "Classroom already " + numberNew + " exists");
            return PAGE_UPDATE;
        }
        classroomService.update(new ClassroomDTO(numberNew), new ClassroomDTO(numberOld));
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        model.addAttribute(MESSAGE_INFO, "Number changed from " + numberOld + " to " + numberNew);
        return PAGE_UPDATE;

    }

    @GetMapping("/update-classroom")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new ClassroomDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping(value = "/delete-classroom")
    public String delete(@ModelAttribute(NAME_ATTRIBUTE_CLASSROOM) @Valid ClassroomDTO classroomDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheck = classroomService.findAll().stream().anyMatch(findClassroomDTO -> findClassroomDTO.getNumberClassroom() == classroomDTO.getNumberClassroom());
        if (!existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
            model.addAttribute(MESSAGE_INFO, "No such class found for deletion " + classroomDTO.getNumberClassroom());
            return PAGE_DELETE;
        }

        int idClassroom = classroomService.findAll().stream().filter(p -> p.getNumberClassroom() == classroomDTO.getNumberClassroom()).findFirst().get().getId();
        boolean includedInTheSchedule = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getClassroomDTO().getId() == idClassroom);

        if (includedInTheSchedule) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "Cannot delete while class is on schedule");
            model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
            return PAGE_DELETE;
        }
        classroomService.delete(classroomDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        model.addAttribute(MESSAGE_INFO, "Deleted number classroom  " + classroomDTO.getNumberClassroom());
        return PAGE_DELETE;
    }

    @GetMapping(value = "/delete-classroom")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new ClassroomDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
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
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new ClassroomDTO());
        String nameMethod = error.getViolation().get(0).getFieldName().split("\\.")[0];
        String errorMessage = error.getViolation().get(0).getMessage();
        model.addAttribute(HAS_ERRORS, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        model.addAttribute(MESSAGE_INFO, errorMessage);
        if (nameMethod.equals("create")) {
            return PAGE_CREATE;
        } else if (nameMethod.equals("delete")) {
            return PAGE_DELETE;
        }
        return PAGE_UPDATE;
    }
}
