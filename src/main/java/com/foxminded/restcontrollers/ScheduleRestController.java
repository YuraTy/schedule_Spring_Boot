package com.foxminded.restcontrollers;

import com.foxminded.dto.ScheduleDTO;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "apiSchedule")
public class ScheduleRestController {

    @Autowired
    private ScheduleService scheduleService;

    @PutMapping(value = "/apiCreate")
    @ResponseBody
    public ScheduleDTO create(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.create(scheduleDTO);
    }

    @GetMapping(value = "/apiFindAll")
    @ResponseBody
    public List<ScheduleDTO> findAll() {
        return scheduleService.findAll();
    }

    @GetMapping(value = "/apiFindScheduleToTeacher/{idTeacher}")
    @ResponseBody
    public List<ScheduleDTO> findScheduleToTeacher(@PathVariable int idTeacher) {
        return scheduleService.takeScheduleToTeacher(new TeacherDTO(idTeacher));
    }

    @PostMapping(value = "/apiUpdate/{idOld}")
    @ResponseBody
    public ScheduleDTO update(@RequestBody ScheduleDTO scheduleNew, @PathVariable int idOld) {
        return scheduleService.update(scheduleNew,new ScheduleDTO(idOld));
    }

    @DeleteMapping(value = "/apiDelete/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        scheduleService.delete(new ScheduleDTO(id));
    }
}
