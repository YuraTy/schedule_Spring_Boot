package com.foxminded.controllers;

import com.foxminded.model.Teacher;
import com.foxminded.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/createTeacher")
    public String create(@ModelAttribute Teacher teacher) {
        teacherService.create(teacher);
        return "redirect:/create-teacher";
    }

    @GetMapping("/allTeachers")
    public String findAll(Model model) {
        model.addAttribute("allTeachers",teacherService.findAll());
        return "create-teacher";
    }

    @PutMapping("/updateTeacher")
    public String update(@ModelAttribute Teacher teacherNew , Teacher teacherOld) {
        teacherService.update(teacherNew, teacherOld);
        return "redirect:/create-teacher";
    }

    @DeleteMapping("/deleteTeacher")
    public String delete(@ModelAttribute Teacher teacher) {
        teacherService.delete(teacher);
        return HttpStatus.OK.toString();
    }
}
