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

    private static final String PAGE_GROUP = "page-group";

    @PostMapping("/create-group")
    public String create(@ModelAttribute Group group) {
        groupService.create(group);
        return PAGE_GROUP;
    }

    @GetMapping("/all-groups")
    public String findAll(Model model) {
        model.addAttribute("allGroup",groupService.findAll());
        return PAGE_GROUP;
    }

    @PutMapping("/update-group")
    public String update(@ModelAttribute Group groupNew , Group groupOld) {
        groupService.update(groupNew, groupOld);
        return PAGE_GROUP;
    }

    @DeleteMapping("/delete-group")
    public String delete(Group group) {
        groupService.delete(group);
        return PAGE_GROUP;
    }
}
