package com.foxminded.controllers;

import com.foxminded.model.Schedule;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @GetMapping("/sample")
    public String create(Schedule schedule) {
        return scheduleService.create(schedule).getCourse().getNameCourse();
    }

}
