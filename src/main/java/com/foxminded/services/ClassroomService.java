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
        logger.info("Data correct input");
        return mapping(classroomDao.create(classroom));
    }

    public List<ClassroomDTO> findAll() {
        try {
            List<ClassroomDTO> classroomDTOLIst =  classroomDao.findAll().stream()
                    .map(p -> mapping(p))
                    .collect(Collectors.toList());
            logger.info("The data is correctly found in the database");
            return classroomDTOLIst;
        } catch (Exception exception) {
            logger.error("Data in database not found");
            exception.getMessage();
        }
        return new ArrayList<>();
    }

    public ClassroomDTO update(Classroom classroomNew, Classroom classroomOld) {
        try {
            logger.info("Attempt to update data");
            return mapping(classroomDao.update(classroomNew, classroomOld));
        }catch (Exception e) {
            logger.warn("Replacement data not found");
        }
        return mapping(classroomNew);
    }

    public void delete(Classroom classroom) {
        try {
            classroomDao.delete(classroom);
            logger.info("Data deleted successfully");
        } catch (Exception e) {
            logger.warn(String.format("No entry found to delete : classroom № %d", classroom.getNumberClassroom()));
        }
    }

    private ClassroomDTO mapping(Classroom classroom) {
        return modelMapper.map(classroom, ClassroomDTO.class);
    }
}
