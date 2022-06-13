package com.foxminded.dto;

public class GroupDTO {

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
