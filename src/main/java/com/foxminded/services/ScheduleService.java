package com.foxminded.services;

import com.foxminded.commonserviceexception.CommonServiceException;
import com.foxminded.dao.ScheduleDaoImpl;
import com.foxminded.dto.ScheduleDTO;
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
    private ScheduleDaoImpl scheduleDao;

    private final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    public ScheduleDTO create(Schedule schedule) {
        ScheduleDTO scheduleDTO = mapping(scheduleDao.create(schedule));
        logger.info("Data entered into the database using the ( create ) method");
        logger.trace("Added data group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {} to the database, Returned DTO object with data group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {}", schedule.getGroup().getGroupId(),schedule.getTeacher().getTeacherId(),schedule.getCourse().getCourseId(),schedule.getClassroom().getClassroomId(),schedule.getLessonStartTime(),schedule.getLessonEndTime(),scheduleDTO.getGroup().getGroupId(),scheduleDTO.getTeacher().getTeacherId(),scheduleDTO.getCourse().getCourseId(),scheduleDTO.getClassroom().getClassroomId(),scheduleDTO.getLessonStartTime(),scheduleDTO.getLessonEndTime());
        return scheduleDTO;
    }

    public List<ScheduleDTO> findAll() {
        try {
            List<ScheduleDTO> scheduleDTOList = scheduleDao.findAll().stream()
                    .map(p -> mapping(p))
                    .peek(p -> logger.trace("Found data schedule id ={}, group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {} to the database, Returned DTO object with data schedule id ={}, group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {}", p.getScheduleId(),p.getGroup().getGroupId(),p.getTeacher().getTeacherId(),p.getCourse().getCourseId(),p.getClassroom().getClassroomId(),p.getLessonStartTime(),p.getLessonEndTime(),p.getScheduleId(),p.getGroup().getGroupId(),p.getTeacher().getTeacherId(),p.getCourse().getCourseId(),p.getClassroom().getClassroomId(),p.getLessonStartTime(),p.getLessonEndTime()))
                    .collect(Collectors.toList());
            if (scheduleDTOList.isEmpty()){
                throw new CommonServiceException();
            }
            logger.info("The data is correctly found in the database using the method ( findAll )");
            return scheduleDTOList;
        }catch (CommonServiceException e){
            logger.error("Error while querying the database: {} , {}", e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    public List<ScheduleDTO> takeScheduleToTeacher(Teacher teacher) {
        try {
            List<ScheduleDTO> scheduleDTOList =scheduleDao.takeScheduleToTeacher(teacher).stream()
                    .map(p -> mapping(p))
                    .peek(p -> logger.trace("Found data schedule id ={}, group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {} to the database, Returned DTO object with data schedule id ={}, group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {}", p.getScheduleId(),p.getGroup().getGroupId(),p.getTeacher().getTeacherId(),p.getCourse().getCourseId(),p.getClassroom().getClassroomId(),p.getLessonStartTime(),p.getLessonEndTime(),p.getScheduleId(),p.getGroup().getGroupId(),p.getTeacher().getTeacherId(),p.getCourse().getCourseId(),p.getClassroom().getClassroomId(),p.getLessonStartTime(),p.getLessonEndTime()))
                    .collect(Collectors.toList());
            if (scheduleDTOList.isEmpty()){
                throw new CommonServiceException();
            }
            logger.info("The data is correctly found in the database using the method ( takeScheduleToTeacher )");
            return scheduleDTOList;
        }catch (CommonServiceException e){
            logger.error("Error while querying the database: {} , {}", e.getMessage(), e.getStackTrace());
        }
        return new ArrayList<>();
    }

    public ScheduleDTO update(Schedule scheduleNew, Schedule scheduleOld) {
        try {
            ScheduleDTO scheduleDTO;
            if ((scheduleDTO = mapping(scheduleDao.update(scheduleNew, scheduleOld))) == null){
                throw new CommonServiceException();
            }
            logger.info("Data updated using the (update) method");
            logger.trace("The data in the database has been changed from group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {} to group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {} ", scheduleOld.getGroup().getGroupId(),scheduleOld.getTeacher().getTeacherId(),scheduleOld.getCourse().getCourseId(),scheduleOld.getClassroom().getClassroomId(),scheduleOld.getLessonStartTime(),scheduleOld.getLessonEndTime(),scheduleNew.getGroup().getGroupId(),scheduleNew.getTeacher().getTeacherId(),scheduleNew.getCourse().getCourseId(),scheduleNew.getClassroom().getClassroomId(),scheduleNew.getLessonStartTime(),scheduleNew.getLessonEndTime());
            return scheduleDTO;
        }catch (CommonServiceException e){
            logger.warn("Could not find data in database to replace group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {}", scheduleOld.getGroup().getGroupId(),scheduleOld.getTeacher().getTeacherId(),scheduleOld.getCourse().getCourseId(),scheduleOld.getClassroom().getClassroomId(),scheduleOld.getLessonStartTime(),scheduleOld.getLessonEndTime());
            logger.error("Error when accessing the database : {} , {}", e.getMessage(), e.getStackTrace());
        }
        return mapping(scheduleNew);
    }

    public void delete(Schedule schedule) {
        try {
            try {
                scheduleDao.delete(schedule);
                logger.info("Data deleted successfully group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {}", schedule.getGroup().getGroupId(),schedule.getTeacher().getTeacherId(),schedule.getCourse().getCourseId(),schedule.getClassroom().getClassroomId(),schedule.getLessonStartTime(),schedule.getLessonEndTime());
            } catch (Exception e) {
                throw new CommonServiceException();
            }
        } catch (CommonServiceException e) {
            logger.warn("Data in database group id = {}, teacher id = {}, course id = {}, classroom id = {}, start time = {}, end time = {} not found for deletion", schedule.getGroup().getGroupId(),schedule.getTeacher().getTeacherId(),schedule.getCourse().getCourseId(),schedule.getClassroom().getClassroomId(),schedule.getLessonStartTime(),schedule.getLessonEndTime());
            logger.error("Error while deleting data from database: {} , {}", e.getStackTrace(), e.getMessage());
        }
    }

    private ScheduleDTO mapping(Schedule schedule) {
        return modelMapper.map(schedule,ScheduleDTO.class);
    }
}
