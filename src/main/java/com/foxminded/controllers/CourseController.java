package com.foxminded.controllers;


import com.foxminded.model.Course;
import com.foxminded.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Component
@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/createCourse")
    public String create(@ModelAttribute Course course) {
        courseService.create(course);
        return "redirect:/create-course";
    }

    @GetMapping("/allCourses")
    public String findAll(Model model) {
        model.addAttribute("allCourses",courseService.findAll());
        return "create-course";
    }

    @PutMapping("/updateCourse")
    public String update(@ModelAttribute Course courseNew, Course courseOld) {
        courseService.update(courseNew, courseOld);
        return "redirect:/create-course";
    }

    @DeleteMapping("/deleteCourse")
    public String delete(@ModelAttribute Course course) {
        courseService.delete(course);
        return HttpStatus.OK.toString();
    }
}
