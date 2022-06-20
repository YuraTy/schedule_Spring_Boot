package com.foxminded.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class GroupDTO {

    @NotBlank(message = "The field cannot be empty")
    @Size(min = 5,max = 10,message = "The name must contain from 5 to 10 characters, for example AA-00")
    private String nameGroup;

    private int id;

    public GroupDTO(String nameGroup, int id) {
        this.nameGroup = nameGroup;
        this.id = id;
    }

    public GroupDTO() {
    }

    public GroupDTO(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
