package com.foxminded.controllers;

import com.foxminded.model.Classroom;
import com.foxminded.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;

    @PostMapping("/createClassroom")
    public String create(@ModelAttribute Classroom classroom) {
        classroomService.create(classroom);
        return "redirect:/create-classroom";
    }

    @GetMapping("/allClassrooms")
    public String findAll(Model model) {
        model.addAttribute("allClassrooms",classroomService.findAll());
        return "create-classroom";
    }

    @PutMapping("/updateClassroom")
    public String update(@ModelAttribute Classroom classroomNew, Classroom classroomOld) {
        classroomService.update(classroomNew, classroomOld);
        return "redirect:/create-classroom";
    }

    @DeleteMapping("/deleteClassroom")
    public void delete(@ModelAttribute Classroom classroom) {
        classroomService.delete(classroom);
    }
}
