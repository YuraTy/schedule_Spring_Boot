package com.foxminded.controllers;

import com.foxminded.dto.GroupDTO;
import com.foxminded.services.GroupService;
import com.foxminded.services.ScheduleService;
import com.foxminded.violation.ValidationErrorResponse;
import com.foxminded.violation.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Controller
@RequestMapping("group")
@Validated
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
    public String create(@ModelAttribute(NAME_ATTRIBUTE_GROUP) @Valid GroupDTO groupDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheck = groupService.findAll().stream().anyMatch(findGroupDTO -> findGroupDTO.getNameGroup().equals(groupDTO.getNameGroup()));
        if (existenceCheck) {
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
    public String update(@RequestParam(required = false, name = "groupNew") @Size(min = 5, max = 10, message = "The name must contain from 5 to 10 characters, for example AA-00") String groupNew,
                         @RequestParam(required = false, name = "groupOld") String groupOld,
                         Model model) {
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
    public String delete(@ModelAttribute(NAME_ATTRIBUTE_GROUP) @Valid GroupDTO groupDTO, BindingResult bindingResult, Model model) {
        boolean existenceCheck = groupService.findAll().stream().anyMatch(findGroupDTO -> findGroupDTO.getNameGroup().equals(groupDTO.getNameGroup()));
        if (!existenceCheck) {
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String errorResponse(ConstraintViolationException e, Model model) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolation().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        String nameMethod = error.getViolation().get(0).getFieldName().split("\\.")[0];
        String errorMessage = error.getViolation().get(0).getMessage();
        model.addAttribute(NAME_ATTRIBUTE_GROUP, new GroupDTO());
        model.addAttribute(HAS_ERRORS, true);
        model.addAttribute(NAME_ATTRIBUTE_ALL, groupService.findAll());
        model.addAttribute(MESSAGE_INFO, errorMessage);
        if (nameMethod.equals("create")) {
            return PAGE_CREATE;
        } else if (nameMethod.equals("delete")) {
            return PAGE_DELETE;
        }
        return PAGE_UPDATE;
    }
}
