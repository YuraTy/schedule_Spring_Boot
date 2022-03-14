package com.foxminded.controllers;


import com.foxminded.model.Course;
import com.foxminded.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    private static final String PAGE_COURSE = "create-course";

    @PostMapping("/createCourse")
    public String create(@ModelAttribute Course course) {
        courseService.create(course);
        return PAGE_COURSE;
    }

    @GetMapping("/allCourses")
    public String findAll(Model model) {
        model.addAttribute("allCourses",courseService.findAll());
        return PAGE_COURSE;
    }

    @PutMapping("/updateCourse")
    public String update(@ModelAttribute Course courseNew, Course courseOld) {
        courseService.update(courseNew, courseOld);
        return PAGE_COURSE;
    }

    @DeleteMapping("/deleteCourse")
    public String delete(@ModelAttribute Course course) {
        courseService.delete(course);
        return PAGE_COURSE;
    }
}
