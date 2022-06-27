package com.foxminded.restcontrollers;

import com.foxminded.dto.ClassroomDTO;
import com.foxminded.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ClassroomRestController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping(value = "/classroom")
    @ResponseBody
    public ClassroomDTO create(@RequestBody @Valid ClassroomDTO classroomDTO) {
        return classroomService.create(classroomDTO);
    }

    @GetMapping(value = "/classroom")
    @ResponseBody
    public List<ClassroomDTO> findAll() {
        return classroomService.findAll();
    }

    @PutMapping(value = "/classroom/{idOld}")
    @ResponseBody
    public ClassroomDTO update(@RequestBody @Valid ClassroomDTO classroomNew , @PathVariable int idOld) {
        return classroomService.update(classroomNew,new ClassroomDTO(idOld));
    }

    @DeleteMapping(value = "/classroom/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        classroomService.delete(new ClassroomDTO(id));
    }
}
