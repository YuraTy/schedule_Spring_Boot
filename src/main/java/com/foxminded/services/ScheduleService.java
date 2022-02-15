package com.foxminded.services;

import com.foxminded.dao.ScheduleDaoImpl;
import com.foxminded.objectdto.ScheduleDTO;
import com.foxminded.schedule.Schedule;
import com.foxminded.teacher.Teacher;
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

    public ScheduleDTO delete(Schedule schedule) {
        scheduleDao.delete(schedule);
        return mapping(schedule);
    }

    private ScheduleDTO mapping(Schedule schedule) {
        return modelMapper.map(schedule,ScheduleDTO.class);
    }
}
