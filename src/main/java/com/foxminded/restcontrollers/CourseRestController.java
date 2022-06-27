package com.foxminded.restcontrollers;

import com.foxminded.dto.CourseDTO;
import com.foxminded.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "apiCourse")
public class CourseRestController {

    @Autowired
    private CourseService courseService;

    @PutMapping(value = "/apiCreate")
    @ResponseBody
    public CourseDTO create(@RequestBody CourseDTO courseDTO) {
        return courseService.create(courseDTO);
    }

    @GetMapping("/apiFindAll")
    @ResponseBody
    public List<CourseDTO> findAll() {
        return courseService.findAll();
    }

    @PostMapping("/apiUpdate/{idOld}")
    @ResponseBody
    public CourseDTO update(@RequestBody @Valid CourseDTO courseNew, @PathVariable int idOld) {
        return courseService.update(courseNew,new CourseDTO(idOld));
    }

    @DeleteMapping("/apiDelete/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        courseService.delete(new CourseDTO(id));
    }

}
