package com.foxminded.controllers;

import com.foxminded.model.Group;
import com.foxminded.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/createGroup")
    public String create(@ModelAttribute Group group) {
        groupService.create(group);
        return "redirect:/create-group";
    }

    @GetMapping("/allGroup")
    public String findAll(Model model) {
        model.addAttribute("allGroup",groupService.findAll());
        return "create-group";
    }

    @PutMapping("/updateGroup")
    public String update(@ModelAttribute Group groupNew , Group groupOld) {
        groupService.update(groupNew, groupOld);
        return "redirect:/create-group";
    }

    @DeleteMapping("/deleteGroup")
    public String delete(Group group) {
        groupService.delete(group);
        return HttpStatus.OK.toString();
    }
}
