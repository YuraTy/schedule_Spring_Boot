package com.foxminded.controllers;


import com.foxminded.model.Course;
import com.foxminded.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private static final String PAGE_COURSE = "page-course";

    @PostMapping(value = "/create-course")
    public String create(@ModelAttribute Course course) {
        courseService.create(course);
        return PAGE_COURSE;
    }

    @GetMapping(value = "/create-course")
    public String create(Model model) {
        model.addAttribute("course",new Course());
        return PAGE_COURSE;
    }

    @GetMapping(value = "/all-course")
    public String findAll(Model model) {
        model.addAttribute("allCourses",courseService.findAll());
        return "all-course";
    }

    @PutMapping("/update-course")
    public String update(@RequestParam(required = false, name = "courseNew") String courseNew,
                         @RequestParam(required = false, name = "courseOld") String courseOld) {
        courseService.update(new Course(courseNew), new Course(courseOld));
        return PAGE_COURSE;
    }

    @DeleteMapping("/delete-course")
    public String delete(@ModelAttribute Course course) {
        courseService.delete(course);
        return PAGE_COURSE;
    }
}
