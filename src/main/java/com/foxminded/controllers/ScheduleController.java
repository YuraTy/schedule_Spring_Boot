package com.foxminded.controllers;

import com.foxminded.model.Schedule;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/createSchedule")
    public String create(@ModelAttribute Schedule schedule) {
        scheduleService.create(schedule);
        return "redirect:/create-schedule";
    }

    @GetMapping("/allSchedule")
    public String findAll(Model model) {
        model.addAttribute("allSchedule",scheduleService.findAll());
        return "create-schedule";
    }

    @PutMapping("/updateSchedule")
    public String update(@ModelAttribute Schedule scheduleNew , Schedule scheduleOld) {
        scheduleService.update(scheduleNew, scheduleOld);
        return "redirect:/create-schedule";
    }

    @DeleteMapping("/deleteSchedule")
    public String delete(@ModelAttribute Schedule schedule) {
        scheduleService.delete(schedule);
        return HttpStatus.OK.toString();
    }
}
