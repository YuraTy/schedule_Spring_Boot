package com.foxminded.dao;

import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;

import java.util.List;

public interface ScheduleDao {

    Schedule create(Schedule schedule);

    List<Schedule> findAll();

    List<Schedule> takeScheduleToTeacher(Teacher teacher);

    Schedule update(Schedule scheduleNew, Schedule scheduleOld);

    void delete(Schedule schedule);
}
