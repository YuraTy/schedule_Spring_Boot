package com.foxminded.restcontrollers;

import com.foxminded.dto.ClassroomDTO;
import com.foxminded.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "apiClassroom")
public class ClassroomRestController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping(value = "/apiCreate")
    @ResponseBody
    public ClassroomDTO create(@RequestBody ClassroomDTO classroomDTO) {
        return classroomService.create(classroomDTO);
    }

    @GetMapping(value = "/apiFindAll")
    @ResponseBody
    public List<ClassroomDTO> findAll() {
        return classroomService.findAll();
    }

    @PutMapping(value = "/apiUpdate/{idOld}")
    @ResponseBody
    public ClassroomDTO update(@RequestBody ClassroomDTO classroomNew , @PathVariable int idOld) {
        return classroomService.update(classroomNew,new ClassroomDTO(idOld));
    }

    @DeleteMapping(value = "/apiDelete/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        classroomService.delete(new ClassroomDTO(id));
    }
}
