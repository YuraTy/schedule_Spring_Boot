package com.foxminded.model;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "GROUPS")
@SqlResultSetMapping(name = "mappingGroup", entities = @EntityResult(entityClass = Group.class))
public class Group {

    @Column(name = "NAME_GROUP")
    private String nameGroup;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
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
