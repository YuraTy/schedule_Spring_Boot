package com.foxminded.services;

import com.foxminded.commonserviceexception.CommonServiceException;
import com.foxminded.dao.CourseDao;
import com.foxminded.model.Course;
import com.foxminded.dto.CourseDTO;
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
public class CourseService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseDao courseDao;

    private final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private static final String ERROR_MESSAGE = "Error while getting data from database in table courses";

    public CourseDTO create(Course course) {
        CourseDTO courseDTO = mapping(courseDao.save(course));
        logger.info("Data entered into the database using the ( create ) method");
        logger.trace("Added data course name = {} to the database, Returned DTO object with data course name = {}",course.getNameCourse(),courseDTO.getNameCourse());
        return courseDTO;
    }

    public List<CourseDTO> findAll() {
        try {
            List<CourseDTO> courseDTOList = courseDao.findAll().stream()
                    .map(p -> mapping(p))
                    .peek(p -> logger.trace("Found data in the database id = {} course name = {}. And the DTO object was created with id = {} , course name = {}",p.getId(),p.getNameCourse(),p.getId(),p.getNameCourse()))
                    .collect(Collectors.toList());
            if (courseDTOList.isEmpty()){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("The data is correctly found in the database using the method ( findAll )");
            return courseDTOList;
        }catch (CommonServiceException e) {
            logger.error("Error while querying the database: {} , {}", e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    public CourseDTO update(Course courseNew, Course courseOld) {
        try {
            Course courseBook = courseDao.findByNameCourse(courseOld.getNameCourse());
            courseBook.setNameCourse(courseNew.getNameCourse());
            CourseDTO courseDTO;
            if ((courseDTO = mapping(courseDao.save(courseBook))) == null){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("Data updated using the (update) method");
            logger.trace("The data in the database has been changed from course name = {} to course name = {}", courseOld.getNameCourse(),courseNew.getNameCourse());
            return courseDTO;
        }catch (CommonServiceException e) {
            logger.warn("Could not find data in database to replace course name = {}", courseOld.getNameCourse());
            logger.error("Error when accessing the database : {} , {}", e.getMessage(), e.getStackTrace());
        }
        return mapping(courseNew);
    }

    public void delete(Course course) {
        try {
            try {
                Course courseBook = courseDao.findByNameCourse(course.getNameCourse());
                courseDao.delete(courseBook);
                logger.info("Data deleted successfully course name = {}", course.getNameCourse());
            }catch (Exception e) {
                throw new CommonServiceException(ERROR_MESSAGE);
            }
        }catch (CommonServiceException e) {
            logger.warn("Data in database course name = {} not found for deletion", course.getNameCourse());
            logger.error("Error while deleting data from database: {} , {}", e.getStackTrace(), e.getMessage());
        }
    }

    private CourseDTO mapping(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }
}
