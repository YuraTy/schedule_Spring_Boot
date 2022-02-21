package com.foxminded.services;

import org.slf4j.Logger;
import com.foxminded.model.Classroom;
import com.foxminded.dao.ClassroomDaoImpl;
import com.foxminded.dto.ClassroomDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomDaoImpl classroomDao;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ClassroomService.class);

    public ClassroomDTO create(Classroom classroom) {
             ClassroomDTO classroomDTO = mapping(classroomDao.create(classroom));
             logger.info("The data is added to the database using the ( create ) method");
        logger.trace("Added data class number = {} to the database, Returned DTO object with data class number = {}",classroom.getNumberClassroom(),classroomDTO.getNumberClassroom());
        return classroomDTO;

    }

    public List<ClassroomDTO> findAll() {
        try {
            List<ClassroomDTO> classroomDTOLIst =  classroomDao.findAll().stream()
                    .map(p -> mapping(p))
                    .peek(p -> logger.trace("Found data in the database id = {} class number = {}. And the DTO object was created with id = {} , class number = {}",p.getClassroomId(),p.getNumberClassroom(),p.getClassroomId(),p.getNumberClassroom()))
                    .collect(Collectors.toList());
            logger.info("The data is correctly found in the database using the method ( findAll )");
            return classroomDTOLIst;
        } catch (Exception exception) {
            Exception exception1 = new Exception("Failed to get data on query request");
            logger.error("Error while querying the database: {} , {}",exception1.getMessage(),exception1.getStackTrace());
        }
        return new ArrayList<>();
    }

    public ClassroomDTO update(Classroom classroomNew, Classroom classroomOld) {
        try {
            ClassroomDTO classroomDTO = mapping(classroomDao.update(classroomNew, classroomOld));
            logger.info("Data updated using the (update) method");
            logger.trace("The data in the database has been changed from class number = {} to class number = {}",classroomOld.getNumberClassroom(),classroomNew.getNumberClassroom());
            return classroomDTO;
        }catch (Exception e) {
            logger.warn("Could not find data in database to replace class number = {}",classroomOld.getNumberClassroom());
            logger.error("Error when accessing the database : {} , {}",e.getMessage(),e.getStackTrace());
        }
        return mapping(classroomNew);
    }

    public void delete(Classroom classroom) {
        try {
            classroomDao.delete(classroom);
            logger.info("Data deleted successfully class number = {}" , classroom.getNumberClassroom());
        } catch (Exception e) {
            logger.warn("Data in DB class number = {} not found for deletion", classroom.getNumberClassroom());
            logger.error("Error while deleting data from database: {} , {}",e.getStackTrace(),e.getMessage());
        }
    }

    private ClassroomDTO mapping(Classroom classroom) {
        return modelMapper.map(classroom, ClassroomDTO.class);
    }
}
