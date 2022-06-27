package com.foxminded.restcontrollers;

import com.foxminded.dto.GroupDTO;
import com.foxminded.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    @PutMapping(value = "/group")
    @ResponseBody
    public GroupDTO create(@RequestBody @Valid GroupDTO groupDTO) {
        return groupService.create(groupDTO);
    }

    @GetMapping(value = "/group")
    @ResponseBody
    public List<GroupDTO> findAll() {
        return groupService.findAll();
    }

    @PostMapping(value = "/group/{idOld}")
    @ResponseBody
    public GroupDTO update(@RequestBody @Valid GroupDTO groupNew, @PathVariable int idOld) {
        return groupService.update(groupNew,new GroupDTO(idOld));
    }

    @DeleteMapping(value = "/group/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        groupService.delete(new GroupDTO(id));
    }
}
