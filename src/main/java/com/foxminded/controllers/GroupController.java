package com.foxminded.controllers;

import com.foxminded.dto.GroupDTO;
import com.foxminded.model.Group;
import com.foxminded.services.GroupService;
import com.foxminded.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ScheduleService scheduleService;

    private static final String NAME_ATTRIBUTE_GROUP = "groupDTO";
    private static final String NAME_ATTRIBUTE_ALL = "allGroup";
    private static final String PAGE_UPDATE = "page-group-update";
    private static final String PAGE_CREATE = "page-group-create";
    private static final String PAGE_DELETE = "page-group-delete";
    private static final String MESSAGE_INFO = "message";
    private static final String HAS_ERRORS = "hasErrors";
    private static final String SAVE_ALL = "savedSuccessful";

    @PostMapping("/create-group")
    public String create(Model model, @ModelAttribute(NAME_ATTRIBUTE_GROUP) GroupDTO groupDTO, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_GROUP, new GroupDTO());
        boolean existenceCheck = groupService.findAll().stream().anyMatch(findGroupDTO -> findGroupDTO.getNameGroup().equals(groupDTO.getNameGroup()));
        if (bindingResult.hasErrors() || existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
            model.addAttribute(MESSAGE_INFO, "Such a group already exists");
            return PAGE_CREATE;
        }
        groupService.create(groupDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
        model.addAttribute(MESSAGE_INFO, "Added group " + groupDTO.getNameGroup());
        return PAGE_CREATE;
    }

    @GetMapping("/create-group")
    public String getCrete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_GROUP, new GroupDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
        return PAGE_CREATE;
    }

    @GetMapping("/all-group")
    public String findAll(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_GROUP, new GroupDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
        return PAGE_CREATE;
    }

    @PostMapping("/update-group")
    public String update(@RequestParam(required = false, name = "groupNew") String groupNew,
                         @RequestParam(required = false, name = "groupOld") String groupOld,
                         Model model) {
        model.addAttribute(NAME_ATTRIBUTE_GROUP, new GroupDTO());
        boolean existenceCheckOld = groupService.findAll().stream().anyMatch(groupDTO -> groupDTO.getNameGroup().equals(groupOld));
        boolean existenceCheckNew = groupService.findAll().stream().anyMatch(groupDTO -> groupDTO.getNameGroup().equals(groupNew));
        try {
            if (!existenceCheckOld) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "Group with name " + groupOld + " not found");
                return PAGE_UPDATE;
            } else if (existenceCheckNew) {
                model.addAttribute(HAS_ERRORS, true);
                model.addAttribute(MESSAGE_INFO, "Group already " + groupNew + " exists");
                return PAGE_UPDATE;
            }
            groupService.update(new GroupDTO(groupNew), new GroupDTO(groupOld));
            model.addAttribute(SAVE_ALL, true);
            model.addAttribute(MESSAGE_INFO, "Group changed from " + groupOld + " to " + groupNew);
            return PAGE_UPDATE;
        } finally {
            model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
        }
    }

    @GetMapping("/update-group")
    public String getUpdate(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_GROUP, new GroupDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
        return PAGE_UPDATE;
    }

    @PostMapping("/delete-group")
    public String delete(Model model, @ModelAttribute(NAME_ATTRIBUTE_GROUP) GroupDTO groupDTO, BindingResult bindingResult) {
        model.addAttribute(NAME_ATTRIBUTE_GROUP, new GroupDTO());
        boolean existenceCheck = groupService.findAll().stream().anyMatch(findGroupDTO -> findGroupDTO.getNameGroup().equals(groupDTO.getNameGroup()));
        if (bindingResult.hasErrors() || !existenceCheck) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
            model.addAttribute(MESSAGE_INFO, "No such group found for deletion " + groupDTO.getNameGroup());
            return PAGE_DELETE;
        }

        int idGroup = groupService.findAll().stream().filter(p -> p.getNameGroup().equals(groupDTO.getNameGroup())).findFirst().get().getId();
        boolean includedInTheSchedule = scheduleService.findAll().stream().anyMatch(scheduleDTO -> scheduleDTO.getClassroomDTO().getId() == idGroup);

        if (includedInTheSchedule) {
            model.addAttribute(HAS_ERRORS, true);
            model.addAttribute(MESSAGE_INFO, "Cannot delete while group is on schedule");
            model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
            return PAGE_DELETE;
        }
        groupService.delete(groupDTO);
        model.addAttribute(SAVE_ALL, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
        model.addAttribute(MESSAGE_INFO, "Deleted group " + groupDTO.getNameGroup());
        return PAGE_DELETE;
    }

    @GetMapping("/delete-group")
    public String getDelete(Model model) {
        model.addAttribute(NAME_ATTRIBUTE_GROUP, new GroupDTO());
        model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
        return PAGE_DELETE;
    }
}
