package com.foxminded.controllers;

import com.foxminded.model.Classroom;
import com.foxminded.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    private static final String PAGE_CLASSROOM = "page-classroom";
    private static final String NAME_ATTRIBUTE_CLASSROOM = "classroom";

    @PostMapping(value = "/create-classroom")
    public String create(Model model, @ModelAttribute("classroom") Classroom classroom) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        classroomService.create(classroom);
        return PAGE_CLASSROOM;
    }

    @GetMapping(value = "/create-classroom")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        return PAGE_CLASSROOM;
    }

    @RequestMapping("/all-classroom")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        model.addAttribute("allClassrooms", classroomService.findAll());
        return PAGE_CLASSROOM;
    }

    @PostMapping("/update-classroom")
    public String update(@RequestParam(required = false, name = "numberNew") Integer numberNew,
                         @RequestParam(required = false, name = "numberOld") Integer numberOld,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        classroomService.update(new Classroom(numberNew), new Classroom(numberOld));
        return PAGE_CLASSROOM;
    }

    @GetMapping("/update-classroom")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        return PAGE_CLASSROOM;
    }

    @PostMapping(value = "/delete-classroom")
    public String delete(Model model, @ModelAttribute("classroom") Classroom classroom) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        classroomService.delete(classroom);
        return PAGE_CLASSROOM;
    }

    @GetMapping(value = "/delete-classroom")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_CLASSROOM, new Classroom());
        return PAGE_CLASSROOM;
    }
}
