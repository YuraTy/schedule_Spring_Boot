package com.foxminded.controllers;

import com.foxminded.model.Classroom;
import com.foxminded.services.ClassroomService;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ScheduleService scheduleService;

    private static final String NAME_ATTRIBUTE_CLASSROOM = "classroom";
    private static final String NAME_ATTRIBUTE_ALL = "allClassrooms";
    private static final String PAGE_UPDATE = "page-classroom-update";
    private static final String PAGE_CREATE = "page-classroom-create";
    private static final String PAGE_DELETE = "page-classroom-delete";
    private static final String MESSAGE_INFO = "message";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SAVE_ALL = "savedSuccessful";

    @PostMapping(value = "/create-classroom")
    public String create(Model model, @ModelAttribute(NAME_ATTRIBUTE_CLASSROOM) Classroom classroom, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        boolean existenceCheck = classroomService.findAll().stream().anyMatch(classroomDTO -> classroomDTO.getNumberClassroom() == classroom.getNumberClassroom());
        if (bindingResult.hasErrors() || existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
            model.addAttribute(MESSAGE_INFO, "Such a class already exists");
            return PAGE_CREATE;
        }
        model.addAttribute(SAVE_ALL, true);
        classroomService.create(classroom);
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        model.addAttribute(MESSAGE_INFO, "Added number classroom  " + classroom.getNumberClassroom());
        return PAGE_CREATE;
    }

    @GetMapping(value = "/create-classroom")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom(0));
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        return PAGE_CREATE;
    }

    @RequestMapping("/all-classroom")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        return PAGE_CREATE;
    }

    @PostMapping("/update-classroom")
    public String update(@RequestParam(required = false, name = "numberNew") Integer numberNew,
                         @RequestParam(required = false, name = "numberOld") Integer numberOld,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
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
        classroomService.update(new Classroom(numberNew), new Classroom(numberOld));
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        model.addAttribute(MESSAGE_INFO, "Number changed from " + numberOld + " to " + numberNew);
        return PAGE_UPDATE;
    }

    @GetMapping("/update-classroom")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping(value = "/delete-classroom")
    public String delete(Model model, @ModelAttribute(NAME_ATTRIBUTE_CLASSROOM) Classroom classroom, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        boolean existenceCheck = classroomService.findAll().stream().anyMatch(classroomDTO -> classroomDTO.getNumberClassroom() == classroom.getNumberClassroom());
        if (bindingResult.hasErrors() || !existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
            model.addAttribute(MESSAGE_INFO, "No such class found for deletion " + classroom.getNumberClassroom());
            return PAGE_DELETE;
        }

        int idClassroom = classroomService.findAll().stream().filter(p -> p.getNumberClassroom() == classroom.getNumberClassroom()).findFirst().get().getId();
        boolean includedInTheSchedule = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getClassroom().getId() == idClassroom);

        if (includedInTheSchedule) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "Cannot delete while class is on schedule");
            model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
            return PAGE_DELETE;
        }
        classroomService.delete(classroom);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        model.addAttribute(MESSAGE_INFO, "Deleted number classroom  " + classroom.getNumberClassroom());
        return PAGE_DELETE;
    }

    @GetMapping(value = "/delete-classroom")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        model.addAttribute(NAME_ATTRIBUTE_ALL, classroomService.findAll());
        return PAGE_DELETE;
    }
}
