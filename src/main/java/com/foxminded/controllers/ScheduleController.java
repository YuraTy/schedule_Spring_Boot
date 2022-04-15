package com.foxminded.controllers;

import com.foxminded.dto.ScheduleDTO;
import com.foxminded.model.*;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    private static final String PAGE_SCHEDULE = "page-schedule";
    private static final String NAME_ATTRIBUTE_SCHEDULE = "schedule";
    private static final String NAME_ATTRIBUTE_TEACHER = "teacher";

    @PostMapping("/create-schedule")
    public String create(@ModelAttribute("schedule") Schedule schedule, Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        scheduleService.create(schedule);
        return PAGE_SCHEDULE;
    }

    @GetMapping("/create-schedule")
    public String getCreate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        return PAGE_SCHEDULE;
    }

    @GetMapping("/all-schedule")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        model.addAttribute("allSchedule", scheduleService.findAll());
        return PAGE_SCHEDULE;
    }

    @PostMapping("/take-schedule-to-teacher")
    public String takeScheduleToTeacher(@ModelAttribute("teacher") Teacher teacher, Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        List<ScheduleDTO> scheduleList = scheduleService.takeScheduleToTeacher(teacher);
        model.addAttribute("titleNameTeacher", scheduleList.get(0).getTeacher().getFirstName());
        model.addAttribute("takeScheduleToTeacher", scheduleList);
        return "schedule-to-teacher";
    }

    @GetMapping("/take-schedule-to-teacher")
    public String getTakeScheduleToTeacher(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        return PAGE_SCHEDULE;
    }

    @PostMapping("/update-schedule")
    public String update(@RequestParam("idOldSchedule") int idOldSchedule,
                         @ModelAttribute("schedule") Schedule schedule,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        scheduleService.update(schedule, new Schedule(idOldSchedule));
        return PAGE_SCHEDULE;
    }

    @GetMapping("/update-schedule")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        return PAGE_SCHEDULE;
    }

    @PostMapping("/delete-schedule")
    public String delete(@ModelAttribute("schedule") Schedule schedule, Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        scheduleService.delete(schedule);
        return PAGE_SCHEDULE;
    }

    @GetMapping("/delete-schedule")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_SCHEDULE, new Schedule());
        model.addAttribute(NAME_ATTRIBUTE_TEACHER, new Teacher());
        return PAGE_SCHEDULE;
    }
}
