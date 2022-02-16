package com.foxminded.services;

import com.foxminded.dao.ScheduleDaoImpl;
import com.foxminded.dto.ScheduleDTO;
import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ScheduleDaoImpl scheduleDao;

    public ScheduleDTO create(Schedule schedule) {
        return mapping(scheduleDao.create(schedule));
    }

    public List<ScheduleDTO> findAll() {
        return scheduleDao.findAll().stream()
                .map(p -> mapping(p))
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> takeScheduleToTeacher(Teacher teacher) {
        return scheduleDao.takeScheduleToTeacher(teacher).stream()
                .map(p -> mapping(p))
                .collect(Collectors.toList());
    }

    public ScheduleDTO update(Schedule scheduleNew, Schedule scheduleOld) {
        return mapping(scheduleDao.update(scheduleNew, scheduleOld));
    }

    public void delete(Schedule schedule) {
        scheduleDao.delete(schedule);
    }

    private ScheduleDTO mapping(Schedule schedule) {
        return modelMapper.map(schedule,ScheduleDTO.class);
    }
}
