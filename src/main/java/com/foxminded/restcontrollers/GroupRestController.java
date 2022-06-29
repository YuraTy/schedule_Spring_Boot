package com.foxminded.restcontrollers;

import com.foxminded.dto.GroupDTO;
import com.foxminded.services.GroupService;
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

@Tag(name = "Group", description = "The group API")
@RestController
@RequestMapping(value = "/api")
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    @Operation(summary = "Create new group")
    @ApiResponse(responseCode = "200", description = "Added group", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = GroupDTO.class)))
    @PutMapping(value = "/group")
    @ResponseBody
    public GroupDTO create(@RequestBody @Valid GroupDTO groupDTO) {
        return groupService.create(groupDTO);
    }

    @Operation(summary = "Get all groups")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200",
            description = "Found the groups",
            content = @Content
                    (mediaType = "application/json", array = @ArraySchema
                            (schema = @Schema(implementation = GroupDTO.class)))))
    @GetMapping(value = "/group")
    @ResponseBody
    public List<GroupDTO> findAll() {
        return groupService.findAll();
    }

    @Operation(summary = "Update group")
    @Parameter(name = "oldName", description = "Name to be replaced ")
    @ApiResponse(responseCode = "200", description = "Updated group", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = GroupDTO.class)))
    @PostMapping(value = "/group/{oldName}")
    @ResponseBody
    public GroupDTO update(@RequestBody @Valid GroupDTO groupNew, @PathVariable String  oldName) {
        return groupService.update(groupNew,new GroupDTO(oldName));
    }

    @Operation(summary = "Delete group by name")
    @Parameter(name = "name", description = "Name group")
    @ApiResponse(responseCode = "200", description = "Deleted group", content = @Content
            (mediaType = "application/json",schema = @Schema
                    (implementation = GroupDTO.class)))
    @DeleteMapping(value = "/group/{name}")
    @ResponseBody
    public void delete(@PathVariable String name) {
        groupService.delete(new GroupDTO(name));
    }
}
