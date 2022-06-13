package com.foxminded.controllers;


import com.foxminded.dto.CourseDTO;
import com.foxminded.services.CourseService;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScheduleService scheduleService;

    private static final String NAME_ATTRIBUTE_COURSE = "courseDTO";
    private static final String NAME_ATTRIBUTE_ALL = "allCourses";
    private static final String PAGE_UPDATE = "page-course-update";
    private static final String PAGE_CREATE = "page-course-create";
    private static final String PAGE_DELETE = "page-course-delete";
    private static final String MESSAGE_INFO = "message";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SAVE_ALL = "savedSuccessful";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @PostMapping(value = "/create-course")
    public String create(@ModelAttribute(NAME_ATTRIBUTE_COURSE) @Valid CourseDTO courseDTO, BindingResult bindingResult, Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        boolean existenceCheck = courseService.findAll().stream().anyMatch(findCourseDTO -> findCourseDTO.getNameCourse().equals(courseDTO.getNameCourse()));
        if (bindingResult.hasErrors()) {
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            return PAGE_CREATE;
        }
        if (existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "Such a course already exists");
            return PAGE_CREATE;
        }
        courseService.create(courseDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        model.addAttribute(MESSAGE_INFO, "Added course " + courseDTO.getNameCourse());
        return PAGE_CREATE;
    }

    @GetMapping(value = "/create-course")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        return PAGE_CREATE;
    }

    @GetMapping(value = "/all-course")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        return PAGE_CREATE;
    }

    @PostMapping("/update-course")
    public String update(@RequestParam(required = false, name = "courseNew") String courseNew,
                         @RequestParam(required = false, name = "courseOld") String courseOld,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        boolean existenceCheckOld = courseService.findAll().stream().anyMatch(courseDTO -> courseDTO.getNameCourse().equals(courseOld));
        boolean existenceCheckNew = courseService.findAll().stream().anyMatch(courseDTO -> courseDTO.getNameCourse().equals(courseNew));
        if (!existenceCheckOld) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "Course with name " + courseOld + " not found");
            return PAGE_UPDATE;
        } else if (existenceCheckNew) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "Course already " + courseNew + " exists");
            return PAGE_UPDATE;
        }
        courseService.update(new CourseDTO(courseNew), new CourseDTO(courseOld));
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        model.addAttribute(MESSAGE_INFO, "Course changed from " + courseOld + " to " + courseNew);
        return PAGE_UPDATE;
    }

    @GetMapping("/update-course")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping("/delete-course")
    public String delete(Model model, @ModelAttribute(NAME_ATTRIBUTE_COURSE) CourseDTO courseDTO, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        boolean existenceCheck = courseService.findAll().stream().anyMatch(findCourseDTO -> findCourseDTO.getNameCourse().equals(courseDTO.getNameCourse()));

        if (bindingResult.hasErrors() || !existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "No such course found for deletion " + courseDTO.getNameCourse());
            return PAGE_DELETE;
        }

        int idCourse = courseService.findAll().stream().filter(p -> p.getNameCourse().equals(courseDTO.getNameCourse())).findFirst().get().getId();
        boolean includedInTheSchedule = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getCourseDTO().getId() == idCourse);

        if (includedInTheSchedule) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
            model.addAttribute(MESSAGE_INFO, "Cannot delete while course is on schedule");
            return PAGE_DELETE;
        }
        courseService.delete(courseDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        model.addAttribute(MESSAGE_INFO, "Deleted course " + courseDTO.getNameCourse());
        return PAGE_DELETE;
    }

    @GetMapping("/delete-course")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_COURSE, new CourseDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, courseService.findAll());
        return PAGE_DELETE;
    }
}
