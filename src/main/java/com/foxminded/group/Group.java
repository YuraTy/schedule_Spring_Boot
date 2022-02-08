package com.foxminded.group;

public class Group {

    private String nameGroup;

    private int groupId;

    public Group(String nameGroup, int groupId) {
        this.nameGroup = nameGroup;
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Group(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public Group() {}

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Group))
            return false;
        Group group = (Group) obj;
        return this.nameGroup.equals(group.nameGroup) ;
    }
}
