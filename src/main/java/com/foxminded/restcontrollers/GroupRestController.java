package com.foxminded.restcontrollers;

import com.foxminded.dto.GroupDTO;
import com.foxminded.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/apiGroup")
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    @PutMapping(value = "/apiCreate")
    @ResponseBody
    public GroupDTO create(@RequestBody GroupDTO groupDTO) {
        return groupService.create(groupDTO);
    }

    @GetMapping(value = "/apiFindAll")
    @ResponseBody
    public List<GroupDTO> findAll() {
        return groupService.findAll();
    }

    @PostMapping(value = "/apiUpdate/{idOld}")
    @ResponseBody
    public GroupDTO update(@RequestBody GroupDTO groupNew, @PathVariable int idOld) {
        return groupService.update(groupNew,new GroupDTO(idOld));
    }

    @DeleteMapping(value = "/apiDelete/{id}")
    @ResponseBody
    public void delete(@PathVariable int id) {
        groupService.delete(new GroupDTO(id));
    }
}
