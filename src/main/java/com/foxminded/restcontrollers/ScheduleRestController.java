package com.foxminded.restcontrollers;

import com.foxminded.dto.ScheduleDTO;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.services.ScheduleService;
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

@Tag(name = "Schedule", description = "The schedule API")
@RestController
@RequestMapping(value = "/api")
public class ScheduleRestController {

    @Autowired
    private ScheduleService scheduleService;

    @Operation(summary = "Create new schedule")
    @ApiResponse(responseCode = "200", description = "Added schedule", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = ScheduleDTO.class)))
    @PutMapping(value = "/schedule")
    @ResponseBody
    public ScheduleDTO create(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        return scheduleService.create(scheduleDTO);
    }

    @Operation(summary = "Get all schedule")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200",
            description = "Found the schedule",
            content = @Content
                    (mediaType = "application/json", array = @ArraySchema
                            (schema = @Schema(implementation = ScheduleDTO.class)))))
    @GetMapping(value = "/schedule")
    @ResponseBody
    public List<ScheduleDTO> findAll() {
        return scheduleService.findAll();
    }

    @Operation(summary = "Get schedule for teacher")
    @Parameter(name = "teacherId", description = "Teacher id to search")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200",
            description = "Found the schedule",
            content = @Content
                    (mediaType = "application/json", array = @ArraySchema
                            (schema = @Schema(implementation = ScheduleDTO.class)))))
    @GetMapping(value = "/schedule-teacher/{teacherId}")
    @ResponseBody
    public List<ScheduleDTO> findScheduleToTeacher(@PathVariable int teacherId) {
        return scheduleService.takeScheduleToTeacher(new TeacherDTO(teacherId));
    }

    @Operation(summary = "Update schedule")
    @Parameter(name = "oldId", description = "Id schedule to be replaced ")
    @ApiResponse(responseCode = "200", description = "Updated schedule", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = ScheduleDTO.class)))
    @PostMapping(value = "/schedule/{oldId}")
    @ResponseBody
    public ScheduleDTO update(@RequestBody @Valid ScheduleDTO scheduleNew, @PathVariable int oldId) {
        return scheduleService.update(scheduleNew,new ScheduleDTO(oldId));
    }

    @Operation(summary = "Delete schedule by id")
    @Parameter(name = "id", description = "Schedule id")
    @ApiResponse(responseCode = "200", description = "Deleted schedule", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = ScheduleDTO.class)))
    @DeleteMapping(value = "/schedule/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        scheduleService.delete(new ScheduleDTO(id));
    }
}
