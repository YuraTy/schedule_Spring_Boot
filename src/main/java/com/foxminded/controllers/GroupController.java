package com.foxminded.controllers;

import com.foxminded.model.Group;
import com.foxminded.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    private static final String PAGE_GROUP = "create-group";

    @PostMapping("/createGroup")
    public String create(@ModelAttribute Group group) {
        groupService.create(group);
        return PAGE_GROUP;
    }

    @GetMapping("/allGroups")
    public String findAll(Model model) {
        model.addAttribute("allGroup",groupService.findAll());
        return PAGE_GROUP;
    }

    @PutMapping("/updateGroup")
    public String update(@ModelAttribute Group groupNew , Group groupOld) {
        groupService.update(groupNew, groupOld);
        return PAGE_GROUP;
    }

    @DeleteMapping("/deleteGroup")
    public String delete(Group group) {
        groupService.delete(group);
        return PAGE_GROUP;
    }
}
