package com.foxminded.dao;

import com.foxminded.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDao extends JpaRepository<Group,Long> {

    Group findByNameGroup(String nameGroup);
}
