package com.foxminded.services;

import com.foxminded.commonserviceexception.CommonServiceException;
import com.foxminded.dao.ScheduleDao;
import com.foxminded.dao.TeacherDao;
import com.foxminded.dto.ScheduleDTO;
import com.foxminded.dto.TeacherDTO;
import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ScheduleDao scheduleDao;

    private final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    private static final String ERROR_MESSAGE = "Error while getting data from database in table schedule";
    private static final String LOGGER_TRACE_MESSAGE = "Found data {} to the database, Returned DTO object with data {}";

    public ScheduleDTO create(ScheduleDTO scheduleDTO) {
        scheduleDao.save(mappingDTOInModel(scheduleDTO));
        logger.info("Data entered into the database using the ( create ) method");
        logger.trace(LOGGER_TRACE_MESSAGE,scheduleDTO,scheduleDTO);
        return scheduleDTO;
    }

    public List<ScheduleDTO> findAll() {
        try {
            List<ScheduleDTO> scheduleDTOList = scheduleDao.findAll().stream()
                    .map(p -> mappingModelInDTO(p))
                    .peek(p -> logger.trace(LOGGER_TRACE_MESSAGE,p,p))
                    .collect(Collectors.toList());
            if (scheduleDTOList.isEmpty()){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("The data is correctly found in the database using the method ( findAll )");
            return scheduleDTOList;
        }catch (CommonServiceException e){
            logger.error("Error while querying the database: {} , {}", e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    public List<ScheduleDTO> takeScheduleToTeacher(TeacherDTO teacherDTO) {
        try {
            List<ScheduleDTO> scheduleDTOList =scheduleDao.findByTeacherId(teacherDTO.getId()).stream()
                    .map(p -> mappingModelInDTO(p))
                    .peek(p -> logger.trace(LOGGER_TRACE_MESSAGE,p,p))
                    .collect(Collectors.toList());
            if (scheduleDTOList.isEmpty()){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("The data is correctly found in the database using the method ( takeScheduleToTeacher )");
            return scheduleDTOList;
        }catch (CommonServiceException e){
            logger.error("Error while querying the database: {} , {}", e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    public ScheduleDTO update(ScheduleDTO scheduleNew, ScheduleDTO scheduleOld) {
        try {
            scheduleNew.setScheduleId(scheduleOld.getScheduleId());
            ScheduleDTO scheduleDTO;
            if ((scheduleDTO = mappingModelInDTO(scheduleDao.save(mappingDTOInModel(scheduleNew)))) == null){
                throw new CommonServiceException(ERROR_MESSAGE);
            }
            logger.info("Data updated using the (update) method");
            logger.trace("The data in the database has been changed from scheduleId = {} to {} ", scheduleOld.getScheduleId(),scheduleNew);
            return scheduleDTO;
        }catch (CommonServiceException e){
            logger.warn("Could not find data in database to replace scheduleId = {}",scheduleOld.getScheduleId());
            logger.error("Error when accessing the database : {} , {}", e.getMessage(), e.getStackTrace());
        }
        return scheduleNew;
    }

    public void delete(ScheduleDTO scheduleDTO) {
        try {
            try {
                scheduleDao.delete(mappingDTOInModel(scheduleDTO));
                logger.info("Data deleted successfully schedule id = {}", scheduleDTO.getScheduleId());
            } catch (Exception e) {
                throw new CommonServiceException(ERROR_MESSAGE);
            }
        } catch (CommonServiceException e) {
            logger.warn("Data in database schedule id = {} not found for deletion", scheduleDTO.getScheduleId());
            logger.error("Error while deleting data from database: {} , {}", e.getStackTrace(), e.getMessage());
        }
    }

    private ScheduleDTO mappingModelInDTO(Schedule schedule) {
        return modelMapper.map(schedule,ScheduleDTO.class);
    }

    private Schedule mappingDTOInModel(ScheduleDTO scheduleDTO) {
        return modelMapper.map(scheduleDTO, Schedule.class);
    }
}
