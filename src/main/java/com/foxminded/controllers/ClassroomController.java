package com.foxminded.controllers;

import com.foxminded.model.Classroom;
import com.foxminded.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClassroomController {

    @Autowired
    private  ClassroomService classroomService;

    private static final String PAGE_CLASSROOM = "page-classroom";

    @PostMapping("/create-classroom")
    public String create(@ModelAttribute Classroom classroom) {
        classroomService.create(classroom);
        return PAGE_CLASSROOM;
    }

    @GetMapping("/all-classrooms")
    public String findAll(Model model) {
        model.addAttribute("allClassrooms",classroomService.findAll());
        return PAGE_CLASSROOM;
    }

    @PutMapping("/update-classroom")
    public String update(@ModelAttribute Classroom classroomNew, Classroom classroomOld) {
        classroomService.update(classroomNew, classroomOld);
        return PAGE_CLASSROOM;
    }

    @DeleteMapping("/delete-classroom")
    public String delete(@ModelAttribute Classroom classroom) {
        classroomService.delete(classroom);
        return PAGE_CLASSROOM;
    }
}
