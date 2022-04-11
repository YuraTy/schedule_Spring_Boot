package com.foxminded.controllers;

import com.foxminded.model.Group;
import com.foxminded.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    private static final String PAGE_GROUP = "page-group";

    @PostMapping("/create-group")
    public String create(Model model, @ModelAttribute Group group) {
        model.addAttribute("group",new Group());
        groupService.create(group);
        return PAGE_GROUP;
    }

    @GetMapping("/create-group")
    public String crete(Model model) {
        model.addAttribute("group",new Group());
        return PAGE_GROUP;
    }

    @GetMapping("/all-group")
    public String findAll(Model model) {
        model.addAttribute("group",new Group());
        model.addAttribute("allGroup",groupService.findAll());
        return PAGE_GROUP;
    }

    @PostMapping("/update-group")
    public String update(@RequestParam(required = false, name = "groupNew") String groupNew,
                         @RequestParam(required = false, name = "groupOld") String groupOld,
                         Model model) {
        model.addAttribute("group",new Group());
        groupService.update(new Group(groupNew), new Group(groupOld));
        return PAGE_GROUP;
    }
    @GetMapping("/update-group")
    public String update(Model model) {
        model.addAttribute("group",new Group());
        return PAGE_GROUP;
    }

    @PostMapping("/delete-group")
    public String delete(Model model, @ModelAttribute Group group) {
        model.addAttribute("group",new Group());
        groupService.delete(group);
        return PAGE_GROUP;
    }

    @GetMapping("/delete-group")
    public String delete(Model model) {
        model.addAttribute("group",new Group());
        return PAGE_GROUP;
    }
}
