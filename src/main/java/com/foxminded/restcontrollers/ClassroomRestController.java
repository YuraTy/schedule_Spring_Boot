package com.foxminded.restcontrollers;

import com.foxminded.dto.ClassroomDTO;
import com.foxminded.services.ClassroomService;
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

@Tag(name = "Classroom", description = "The classroom API")
@RestController
@RequestMapping(value = "/api")
public class ClassroomRestController {

    @Autowired
    private ClassroomService classroomService;


    @Operation(summary = "Create new classroom")
    @ApiResponse(responseCode = "200", description = "Added classroom", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = ClassroomDTO.class)))
    @PutMapping(value = "/classroom")
    @ResponseBody
    public ClassroomDTO create(@RequestBody @Valid ClassroomDTO classroomDTO) {
        return classroomService.create(classroomDTO);
    }

    @Operation(summary = "Get all classrooms")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200",
            description = "Found the classrooms",
            content = @Content
                    (mediaType = "application/json", array = @ArraySchema
                            (schema = @Schema(implementation = ClassroomDTO.class)))))
    @GetMapping(value = "/classroom")
    @ResponseBody
    public List<ClassroomDTO> findAll() {
        return classroomService.findAll();
    }

    @Operation(summary = "Update classroom by number")
    @Parameter(name = "oldNumber", description = "Old number classroom")
    @ApiResponse(responseCode = "200", description = "Updated classroom", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = ClassroomDTO.class)))
    @PostMapping(value = "/classroom/{oldNumber}")
    @ResponseBody
    public ClassroomDTO update(@RequestBody @Valid ClassroomDTO classroomNew , @PathVariable Integer oldNumber) {
        return classroomService.update(classroomNew,new ClassroomDTO(oldNumber));
    }

    @Operation(summary = "Delete classroom by number")
    @Parameter(name = "number", description = "Number for delete classroom")
    @ApiResponse(responseCode = "200", description = "Deleted classroom", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = ClassroomDTO.class)))
    @DeleteMapping(value = "/classroom/{number}")
    @ResponseBody
    public void delete(@PathVariable Integer number) {
        classroomService.delete(new ClassroomDTO(number));
    }
}
