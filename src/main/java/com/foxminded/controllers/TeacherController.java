package com.foxminded.controllers;

import com.foxminded.dto.TeacherDTO;
import com.foxminded.model.Teacher;
import com.foxminded.services.ScheduleService;
import com.foxminded.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("teacher")
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
    public String create(Model model, @ModelAttribute(NAME_ATTRIBUTE_TEACHER) TeacherDTO teacherDTO, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        boolean existenceCheck = teacherService.findAll().stream().anyMatch((findTeacherDTO -> findTeacherDTO.getFirstName().equals(teacherDTO.getFirstName()) && (findTeacherDTO.getLastName().equals(teacherDTO.getLastName()))));
        if (bindingResult.hasErrors() || existenceCheck) {
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
    public String update(@RequestParam(required = false, name = "firstNameNew") String firstNameNew,
                         @RequestParam(required = false, name = "lastNameNew") String lastNameNew,
                         @RequestParam(required = false, name = "firstNameOld") String firstNameOld,
                         @RequestParam(required = false, name = "lastNameOld") String lastNameOld,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        TeacherDTO teacherNew = new TeacherDTO(firstNameNew, lastNameNew);
        TeacherDTO teacherOld = new TeacherDTO(firstNameOld, lastNameOld);
        boolean existenceCheckOld = teacherService.findAll().stream().anyMatch((teacherDTO -> teacherDTO.getFirstName().equals(firstNameOld) && (teacherDTO.getLastName().equals(lastNameOld))));
        boolean existenceCheckNew = teacherService.findAll().stream().anyMatch((teacherDTO -> teacherDTO.getFirstName().equals(firstNameNew) && (teacherDTO.getLastName().equals(lastNameNew))));
        try {
            if (!existenceCheckOld) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "Teacher with name " + teacherOld + " not found");
                return PAGE_UPDATE;
            } else if (existenceCheckNew) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "Teacher already " + teacherNew + " exists");
                return PAGE_UPDATE;
            }
            teacherService.update(teacherNew, teacherOld);
            model.addAttribute(SAVE_ALL, true);
            model.addAttribute(MESSAGE_INFO, "Teacher changed from " + teacherOld + " to " + teacherNew);
            return PAGE_UPDATE;
        } finally {
            model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        }
    }

    @GetMapping("/update-teacher")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, teacherService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping("/delete-teacher")
    public String delete(Model model, @ModelAttribute(NAME_ATTRIBUTE_TEACHER) TeacherDTO teacherDTO, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new TeacherDTO());
        boolean existenceCheck = teacherService.findAll().stream().anyMatch((findTeacherDTO -> findTeacherDTO.getFirstName().equals(teacherDTO.getFirstName()) && (findTeacherDTO.getLastName().equals(teacherDTO.getLastName()))));
        if (bindingResult.hasErrors() || !existenceCheck) {
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
}
