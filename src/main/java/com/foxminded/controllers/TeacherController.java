package com.foxminded.controllers;

import com.foxminded.model.Teacher;
import com.foxminded.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    private static final String PAGE_TEACHER = "page-teacher";
    private static final String NAME_ATTRIBUTE_TEACHER = "teacher";

    @PostMapping("/create-teacher")
    public String create(Model model, @ModelAttribute("teacher") Teacher teacher) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER,new Teacher());
        teacherService.create(teacher);
        return PAGE_TEACHER;
    }

    @GetMapping("/create-teacher")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER,new Teacher());
        return PAGE_TEACHER;
    }

    @GetMapping("/all-teacher")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER,new Teacher());
        model.addAttribute("allTeachers",teacherService.findAll());
        return PAGE_TEACHER;
    }

    @PostMapping("/update-teacher")
    public String update(@RequestParam(required = false, name = "firstNameNew") String firstNameNew,
                         @RequestParam(required = false, name = "lastNameNew") String lastNameNew,
                         @RequestParam(required = false, name = "firstNameOld") String firstNameOld,
                         @RequestParam(required = false, name = "lastNameOld") String lastNameOld,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER,new Teacher());
        teacherService.update(new Teacher(firstNameNew,lastNameNew), new Teacher(firstNameOld,lastNameOld));
        return PAGE_TEACHER;
    }

    @GetMapping("/update-teacher")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER,new Teacher());
        return PAGE_TEACHER;
    }

    @PostMapping("/delete-teacher")
    public String delete(Model model, @ModelAttribute("teacher") Teacher teacher) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER,new Teacher());
        teacherService.delete(teacher);
        return PAGE_TEACHER;
    }

    @GetMapping("/delete-teacher")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_TEACHER,new Teacher());
        return PAGE_TEACHER;
    }
}
