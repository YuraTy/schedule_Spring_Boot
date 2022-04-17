package com.foxminded.model;

import lombok.Builder;

@Builder
public class Group {

    private String nameGroup;

    private int id;

    public Group(String nameGroup, int id) {
        this.nameGroup = nameGroup;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
