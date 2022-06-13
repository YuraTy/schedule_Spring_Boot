package com.foxminded.services;

import com.foxminded.commonserviceexception.CommonServiceException;
import com.foxminded.dao.ClassroomDao;
import org.slf4j.Logger;
import com.foxminded.model.Classroom;
import com.foxminded.dto.ClassroomDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassroomService {

    @Autowired
    private ClassroomDao classroomDao;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ClassroomService.class);

    private static final String ERROR_MESSAGE = "Error while getting data from database in table classrooms";

    public ClassroomDTO create(ClassroomDTO classroomDTO) {
        classroomDao.save(mappingDTOInModel(classroomDTO));
        logger.info("The data is added to the database using the ( create ) method");
        logger.trace("Added data class number = {} to the database, Returned DTO object with data class number = {}", classroomDTO.getNumberClassroom(), classroomDTO.getNumberClassroom());
        return classroomDTO;
    }

    public List<ClassroomDTO> findAll() {
        try {
            List<ClassroomDTO> classroomDTOLIst = classroomDao.findAll().stream()
                    .map(p -> mappingModelInDTO(p))
                    .peek(p -> logger.trace("Found data in the database id = {} class number = {}. And the DTO object was created with id = {} , class number = {}", p.getId(), p.getNumberClassroom(), p.getId(), p.getNumberClassroom()))
                    .collect(Collectors.toList());
            if (classroomDTOLIst.isEmpty()) {
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("The data is correctly found in the database using the method ( findAll )");
            return classroomDTOLIst;
        } catch (CommonServiceException e) {
            logger.error("Error while querying the database: {} , {}", e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    public ClassroomDTO update(ClassroomDTO classroomNew, ClassroomDTO classroomOld) {
        try {
            Classroom classroomBook = classroomDao.findByNumberClassroom(classroomOld.getNumberClassroom());
            classroomBook.setNumberClassroom(classroomNew.getNumberClassroom());
            ClassroomDTO classroomDTO;
            if ((classroomDTO = mappingModelInDTO(classroomDao.save(classroomBook))) == null) {
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("Data updated using the (update) method");
            logger.trace("The data in the database has been changed from class number = {} to class number = {}", classroomOld.getNumberClassroom(), classroomNew.getNumberClassroom());
            return classroomDTO;
        } catch (CommonServiceException e) {
            logger.warn("Could not find data in database to replace class number = {}", classroomOld.getNumberClassroom());
            logger.error("Error when accessing the database : {} , {}", e.getMessage(), e.getStackTrace());
        }
        return classroomNew;
    }

    public void delete(ClassroomDTO classroomDTO) {
        try {
            try {
                Classroom classroomBook = classroomDao.findByNumberClassroom(classroomDTO.getNumberClassroom());
                classroomDao.delete(classroomBook);
                logger.info("Data deleted successfully class number = {}", classroomDTO.getNumberClassroom());
            } catch (Exception e) {
                throw new CommonServiceException(ERROR_MESSAGE);
            }
        } catch (CommonServiceException e) {
            logger.warn("Data in database class number = {} not found for deletion", classroomDTO.getNumberClassroom());
            logger.error("Error while deleting data from database: {} , {}", e.getStackTrace(), e.getMessage());
        }
    }

    private ClassroomDTO mappingModelInDTO(Classroom classroom) {
        return modelMapper.map(classroom, ClassroomDTO.class);
    }
    private Classroom mappingDTOInModel(ClassroomDTO classroomDTO) {
        return modelMapper.map(classroomDTO, Classroom.class);
    }
}
