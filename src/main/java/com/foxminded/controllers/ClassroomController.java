package com.foxminded.controllers;

import com.foxminded.model.Classroom;
import com.foxminded.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Component
public class ClassroomController {

    @Autowired
    private  ClassroomService classroomService;

    private static final String PAGE_CLASSROOM = "create-classroom";

    @PostMapping("/createClassroom")
    public String create(@ModelAttribute Classroom classroom) {
        classroomService.create(classroom);
        return PAGE_CLASSROOM;
    }

    @GetMapping("/allClassrooms")
    public String findAll(Model model) {
        model.addAttribute("allClassrooms",classroomService.findAll());
        return PAGE_CLASSROOM;
    }

    @PutMapping("/updateClassroom")
    public String update(@ModelAttribute Classroom classroomNew, Classroom classroomOld) {
        classroomService.update(classroomNew, classroomOld);
        return PAGE_CLASSROOM;
    }

    @DeleteMapping("/deleteClassroom")
    public String delete(@ModelAttribute Classroom classroom) {
        classroomService.delete(classroom);
        return PAGE_CLASSROOM;
    }
}
