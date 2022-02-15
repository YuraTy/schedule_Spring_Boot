package com.foxminded.services;

import com.foxminded.dao.GroupDaoImpl;
import com.foxminded.group.Group;
import com.foxminded.objectdto.GroupDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupDaoImpl groupDao;

    public GroupDTO create(Group group) {
        return mapping(groupDao.create(group));
    }

    public List<GroupDTO> findAll() {
        return groupDao.findAll().stream()
                .map(p -> mapping(p))
                .collect(Collectors.toList());
    }

    public GroupDTO update(Group groupNew, Group groupOld) {
        return mapping(groupDao.update(groupNew, groupOld));
    }

    public GroupDTO delete(Group group) {
        groupDao.delete(group);
        return mapping(group);
    }

    private GroupDTO mapping(Group group) {
        return modelMapper.map(group, GroupDTO.class);
    }
}
