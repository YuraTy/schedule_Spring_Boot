package com.foxminded.services;

import com.foxminded.commonserviceexception.CommonServiceException;
import com.foxminded.dao.GroupDaoImpl;
import com.foxminded.model.Group;
import com.foxminded.dto.GroupDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupDaoImpl groupDao;

    private final Logger logger = LoggerFactory.getLogger(GroupService.class);

    private static final String ERROR_MESSAGE = "Error while getting data from database in table groups";

    public GroupDTO create(Group group) {
        GroupDTO groupDTO = mapping(groupDao.create(group));
        logger.info("Data entered into the database using the ( create ) method");
        logger.trace("Added data group name = {} to the database, Returned DTO object with data group name = {}", group.getNameGroup(), groupDTO.getNameGroup());
        return groupDTO;
    }

    public List<GroupDTO> findAll() {
        try {
            List<GroupDTO> groupDTOList = groupDao.findAll().stream()
                    .map(p -> mapping(p))
                    .peek(p -> logger.trace("Found data group id = {}, group name = {} to the database, Returned DTO object with data group id = {}, group name = {}", p.getId(),p.getNameGroup(), p.getId(),p.getNameGroup()))
                    .collect(Collectors.toList());
            if (groupDTOList.isEmpty()) {
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("The data is correctly found in the database using the method ( findAll )");
            return groupDTOList;
        }catch (CommonServiceException e){
            logger.error("Error while querying the database: {} , {}", e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    public GroupDTO update(Group groupNew, Group groupOld) {
        try {
            GroupDTO groupDTO;
            if ((groupDTO = mapping(groupDao.update(groupNew, groupOld))) == null) {
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("Data updated using the (update) method");
            logger.trace("The data in the database has been changed from group name = {} to group name = {}", groupOld.getNameGroup(), groupNew.getNameGroup());
            return groupDTO;
        }catch (CommonServiceException e){
            logger.warn("Could not find data in database to replace group name = {}", groupOld.getNameGroup());
            logger.error("Error when accessing the database : {} , {}", e.getMessage(), e.getStackTrace());
        }
        return mapping(groupNew);
    }

    public void delete(Group group) {
        try {
            try {
                groupDao.delete(group);
                logger.info("Data deleted successfully group name = {}", group.getNameGroup());
            }catch (Exception e){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
        }catch (CommonServiceException e){
            logger.warn("Data in database group name = {} not found for deletion", group.getNameGroup());
            logger.error("Error while deleting data from database: {} , {}", e.getStackTrace(), e.getMessage());
        }
    }

    private GroupDTO mapping(Group group) {
        return modelMapper.map(group, GroupDTO.class);
    }
}
