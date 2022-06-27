package com.foxminded.restcontrollers;

import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TeacherRestController {

    @Autowired
    private TeacherService teacherService;

    @PutMapping(value = "/teacher")
    @ResponseBody
    public TeacherDTO create(@RequestBody @Valid TeacherDTO teacherDTO) {
        return teacherService.create(teacherDTO);
    }

    @GetMapping(value = "/teacher")
    @ResponseBody
    public List<TeacherDTO> findAll() {
        return teacherService.findAll();
    }

    @PostMapping(value = "/teacher/{idOld}")
    @ResponseBody
    public TeacherDTO update(@RequestBody @Valid TeacherDTO teacherDTO, @PathVariable int idOld) {
        return teacherService.update(teacherDTO,new TeacherDTO(idOld));
    }

    @DeleteMapping(value = "/teacher/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        teacherService.delete(new TeacherDTO(id));
    }
}
