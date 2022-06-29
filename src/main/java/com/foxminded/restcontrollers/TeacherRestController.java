package com.foxminded.restcontrollers;

import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

@Tag(name = "Teacher", description = "The teacher API")
@RestController
@RequestMapping(value = "/api")
public class TeacherRestController {

    @Autowired
    private TeacherService teacherService;

    @Operation(summary = "Create new teacher")
    @ApiResponse(responseCode = "200", description = "Added teacher", content = @Content
            (mediaType = "application/json", schema = @Schema
                    (implementation = TeacherDTO.class)))
    @PutMapping(value = "/teacher")
    @ResponseBody
    public TeacherDTO create(@RequestBody @Valid TeacherDTO teacherDTO) {
        return teacherService.create(teacherDTO);
    }

    @Operation(summary = "Get all teachers")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200",
            description = "Found the teachers",
            content = @Content
                    (mediaType = "application/json", array = @ArraySchema
                            (schema = @Schema(implementation = TeacherDTO.class)))))
    @GetMapping(value = "/teacher")
    @ResponseBody
    public List<TeacherDTO> findAll() {
        return teacherService.findAll();
    }

    @Operation(summary = "Update teacher")
    @Parameters({@Parameter(name = "firstNameOld", description = "Replacement first name"),
            @Parameter(name = "lastNameOld", description = "Replacement last name")})
    @ApiResponse(responseCode = "200", description = "Updated teacher", content = @Content
            (mediaType = "application/json", schema = @Schema
                    (implementation = TeacherDTO.class)))
    @PostMapping(value = "/teacher/{firstNameOld}/{lastNameOld}")
    @ResponseBody
    public TeacherDTO update(@RequestBody @Valid TeacherDTO newTeacher, @PathVariable("firstNameOld") String firstNameOld, @PathVariable("lastNameOld") String lastNameOld) {
        return teacherService.update(newTeacher, new TeacherDTO(firstNameOld, lastNameOld));
    }

    @Operation(summary = "Delete teacher")
    @ApiResponse(responseCode = "200", description = "Deleted teacher", content = @Content
            (mediaType = "application/json", schema = @Schema
                    (implementation = TeacherDTO.class)))
    @DeleteMapping(value = "/teacher")
    @ResponseBody
    public void delete(@RequestBody @Valid TeacherDTO teacherDTO) {
        teacherService.delete(teacherDTO);
    }
}
