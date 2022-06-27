package com.foxminded.dto;

import com.foxminded.model.Group;

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

    public GroupDTO(int id) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof GroupDTO))
            return false;
        GroupDTO groupDTO = (GroupDTO) obj;
        return this.nameGroup.equals(groupDTO.nameGroup) ;
    }
}
