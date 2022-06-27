package com.foxminded.restcontrollers;

import com.foxminded.dto.ScheduleDTO;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ScheduleRestController {

    @Autowired
    private ScheduleService scheduleService;

    @PutMapping(value = "/schedule")
    @ResponseBody
    public ScheduleDTO create(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        return scheduleService.create(scheduleDTO);
    }

    @GetMapping(value = "/schedule")
    @ResponseBody
    public List<ScheduleDTO> findAll() {
        return scheduleService.findAll();
    }

    @GetMapping(value = "/schedule-teacher/{idTeacher}")
    @ResponseBody
    public List<ScheduleDTO> findScheduleToTeacher(@PathVariable int idTeacher) {
        return scheduleService.takeScheduleToTeacher(new TeacherDTO(idTeacher));
    }

    @PostMapping(value = "/schedule/{idOld}")
    @ResponseBody
    public ScheduleDTO update(@RequestBody @Valid ScheduleDTO scheduleNew, @PathVariable int idOld) {
        return scheduleService.update(scheduleNew,new ScheduleDTO(idOld));
    }

    @DeleteMapping(value = "/schedule/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        scheduleService.delete(new ScheduleDTO(id));
    }
}
