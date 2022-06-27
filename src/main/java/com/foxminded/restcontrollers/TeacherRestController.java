package com.foxminded.restcontrollers;

import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/apiTeacher")
public class TeacherRestController {

    @Autowired
    private TeacherService teacherService;

    @PutMapping(value = "/apiCreate")
    @ResponseBody
    public TeacherDTO create(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.create(teacherDTO);
    }

    @GetMapping(value = "/apiFindAll")
    @ResponseBody
    public List<TeacherDTO> findAll() {
        return teacherService.findAll();
    }

    @PostMapping(value = "/apiUpdate/{idOld}")
    @ResponseBody
    public TeacherDTO update(@RequestBody TeacherDTO teacherDTO, @PathVariable int idOld) {
        return teacherService.update(teacherDTO,new TeacherDTO(idOld));
    }

    @DeleteMapping(value = "/apiDelete/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        teacherService.delete(new TeacherDTO(id));
    }
}
