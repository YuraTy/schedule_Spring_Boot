package com.foxminded.restcontrollers;

import com.foxminded.dto.CourseDTO;
import com.foxminded.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Course",description = "The course API")
@RestController
@RequestMapping(value = "/api")
public class CourseRestController {

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Create new course")
    @ApiResponse(responseCode = "200", description = "Added course", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = CourseDTO.class)))
    @PutMapping(value = "/course")
    @ResponseBody
    public CourseDTO create(@RequestBody @Valid CourseDTO courseDTO) {
        return courseService.create(courseDTO);
    }

    @Operation(summary = "Get all courses")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200",
            description = "Found the courses",
            content = @Content
                    (mediaType = "application/json", array = @ArraySchema
                            (schema = @Schema(implementation = CourseDTO.class)))))
    @GetMapping("/course")
    @ResponseBody
    public List<CourseDTO> findAll() {
        return courseService.findAll();
    }

    @Operation(summary = "Update course by name")
    @Parameter(name = "oldName", description = "Name to be replaced")
    @ApiResponse(responseCode = "200", description = "Updated course", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = CourseDTO.class)))
    @PostMapping("/course/{oldName}")
    @ResponseBody
    public CourseDTO update(@RequestBody @Valid CourseDTO courseNew, @PathVariable("oldName") String oldName) {
        return courseService.update(courseNew,new CourseDTO(oldName));
    }

    @Operation(summary = "Delete course by name")
    @Parameter(name = "name", description = "Name course")
    @ApiResponse(responseCode = "200", description = "Deleted course", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = CourseDTO.class)))
    @DeleteMapping("/course/{name}")
    @ResponseBody
    public void delete(@PathVariable String name) {
        courseService.delete(new CourseDTO(name));
    }

}
