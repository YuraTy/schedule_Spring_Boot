package com.foxminded.dao;

import com.foxminded.group.Group;

import java.util.List;

public interface GroupDao {

    Group create(Group group);

    List<Group> findAll();

    Group update(Group groupNew, Group groupOld);

    void delete(Group group);
}
