package com.foxminded.services;

import com.foxminded.dao.ScheduleDaoImpl;
import com.foxminded.schedule.Schedule;
import com.foxminded.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleDaoImpl scheduleDao;

    public Schedule create(Schedule schedule) {
        return scheduleDao.create(schedule);
    }

    public List<Schedule> findAll() {
        return scheduleDao.findAll();
    }

    public List<Schedule> takeScheduleToTeacher(Teacher teacher) {
        return scheduleDao.takeScheduleToTeacher(teacher);
    }

    public Schedule update(Schedule scheduleNew, Schedule scheduleOld) {
        return scheduleDao.update(scheduleNew, scheduleOld);
    }

    public void delete(Schedule schedule) {
        scheduleDao.delete(schedule);
    }
}
