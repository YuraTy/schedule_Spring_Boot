package com.foxminded.services;

import com.foxminded.commonserviceexception.CommonServiceException;
import com.foxminded.dao.TeacherDao;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.model.Teacher;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeacherDao teacherDao;

    private final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    private static final String ERROR_MESSAGE = "Error while getting data from database in table teachers";

    public TeacherDTO create(TeacherDTO teacherDTO) {
        teacherDao.save(mappingDTOInModel(teacherDTO));
        logger.info("Data entered into the database using the ( create ) method");
        logger.trace("Added data teacher first name = {}, teacher last name = {} to the database, Returned DTO object with data teacher first name = {}, teacher last name = {}", teacherDTO.getFirstName(),teacherDTO.getLastName(),teacherDTO.getFirstName(),teacherDTO.getLastName());
        return teacherDTO;
    }

    public List<TeacherDTO> findAll() {
        try {
            List<TeacherDTO> teacherDTOList = teacherDao.findAll().stream()
                    .map(p -> mappingModelInDTO(p))
                    .peek(p -> logger.trace("Found data teacher id = {},teacher first name = {}, teacher last name = {} to the database, Returned DTO object with data teacher id = {}, teacher first name = {}, teacher last name = {}", p.getId(),p.getFirstName(),p.getLastName(),p.getId(),p.getFirstName(),p.getLastName()))
                    .collect(Collectors.toList());
            if (teacherDTOList.isEmpty()){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("The data is correctly found in the database using the method ( findAll )");
            return teacherDTOList;
        }catch (CommonServiceException e){
            logger.error("Error while querying the database: {} , {}", e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    public TeacherDTO update(TeacherDTO teacherNew, TeacherDTO teacherOld) {
        try {
            Teacher teacherBook = teacherDao.findByFirstNameAndLastName(teacherOld.getFirstName(), teacherOld.getLastName());
            teacherBook.setFirstName(teacherNew.getFirstName());
            teacherBook.setLastName(teacherNew.getLastName());
            TeacherDTO teacherDTO;
            if ((teacherDTO = mappingModelInDTO(teacherDao.save(teacherBook))) == null){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("Data updated using the (update) method");
            logger.trace("The data in the database has been changed from teacher first name = {}, teacher last name = {} to teacher first name = {}, teacher last name = {}", teacherOld.getFirstName(),teacherOld.getLastName(),teacherNew.getFirstName(),teacherNew.getLastName());
            return teacherDTO;
        }catch (CommonServiceException e){
            logger.warn("Could not find data in database to replace teacher first name = {}, teacher last name = {}", teacherOld.getFirstName(),teacherOld.getLastName());
            logger.error("Error when accessing the database : {} , {}", e.getMessage(), e.getStackTrace());
        }
        return teacherNew;
    }

    public void delete(TeacherDTO teacherDTO) {
        try {
            try {
                Teacher teacherBook = teacherDao.findByFirstNameAndLastName(teacherDTO.getFirstName(), teacherDTO.getLastName());
                teacherDao.delete(teacherBook);
                logger.info("Data deleted successfully teacher first name = {}, teacher last name = {}", teacherDTO.getFirstName(),teacherDTO.getLastName());
            }catch (Exception e){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
        }catch (CommonServiceException e){
            logger.warn("Could not find data in database to replace teacher first name = {}, teacher last name = {}", teacherDTO.getFirstName(),teacherDTO.getLastName());
            logger.error("Error when accessing the database : {} , {}", e.getMessage(), e.getStackTrace());
        }
    }

    private TeacherDTO mappingModelInDTO(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    private Teacher mappingDTOInModel(TeacherDTO teacherDTO) {
        return modelMapper.map(teacherDTO, Teacher.class);
    }
}
