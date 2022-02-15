package com.foxminded.objectdto;

public class GroupDTO {
    private String nameGroup;
    private int groupId;

    public GroupDTO(String nameGroup, int groupId) {
        this.nameGroup = nameGroup;
        this.groupId = groupId;
    }

    public GroupDTO() {
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


}
