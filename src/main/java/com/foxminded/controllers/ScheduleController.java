package com.foxminded.controllers;

import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    private static final String PAGE_SCHEDULE = "page-schedule";

    @PostMapping("/create-schedule")
    public String create(@ModelAttribute Schedule schedule , Model model) {
        model.addAttribute("createSchedule",scheduleService.create(schedule));
        return PAGE_SCHEDULE;
    }

    @GetMapping("/all-schedule")
    public String findAll(Model model) {
        model.addAttribute("allSchedule", scheduleService.findAll());
        return PAGE_SCHEDULE;
    }

    @GetMapping("/take-schedule-to-teacher")
    public String takeScheduleToTeacher(@ModelAttribute Teacher teacher , Model model) {
        model.addAttribute("takeScheduleToTeacher",scheduleService.takeScheduleToTeacher(teacher));
        return PAGE_SCHEDULE;
    }

    @PutMapping("/update-schedule")
    public String update(@ModelAttribute Schedule scheduleNew, Schedule scheduleOld) {
        scheduleService.update(scheduleNew, scheduleOld);
        return PAGE_SCHEDULE;
    }

    @DeleteMapping("/delete-schedule")
    public String delete(@ModelAttribute Schedule schedule) {
        scheduleService.delete(schedule);
        return PAGE_SCHEDULE;
    }
}
