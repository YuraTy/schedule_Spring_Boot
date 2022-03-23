package com.foxminded.controllers;

import com.foxminded.model.Teacher;
import com.foxminded.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    private static final String PAGE_TEACHER = "page-teacher";

    @PostMapping("/create-teacher")
    public String create(@ModelAttribute Teacher teacher) {
        teacherService.create(teacher);
        return PAGE_TEACHER;
    }

    @GetMapping("/all-teachers")
    public String findAll(Model model) {
        model.addAttribute("allTeachers",teacherService.findAll());
        return PAGE_TEACHER;
    }

    @PutMapping("/update-teacher")
    public String update(@ModelAttribute Teacher teacherNew , Teacher teacherOld) {
        teacherService.update(teacherNew, teacherOld);
        return PAGE_TEACHER;
    }

    @DeleteMapping("/delete-teacher")
    public String delete(@ModelAttribute Teacher teacher) {
        teacherService.delete(teacher);
        return PAGE_TEACHER;
    }
}
