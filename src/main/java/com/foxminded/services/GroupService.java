package com.foxminded.services;

import com.foxminded.dao.GroupDaoImpl;
import com.foxminded.group.Group;
import com.foxminded.mapperconfig.MapperConfig;
import com.foxminded.objectdto.GroupDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupDaoImpl groupDao;

    public Group create(Group group) {
        return groupDao.create(group);
    }

    public List<Group> findAll() {
        return groupDao.findAll();
    }

    public Group update(Group groupNew, Group groupOld) {
        return groupDao.update(groupNew, groupOld);
    }

    public void delete(Group group) {
        groupDao.delete(group);
    }

    public GroupDTO mapping(Group group) {
    return modelMapper.map(group, GroupDTO .class);
    }
}
