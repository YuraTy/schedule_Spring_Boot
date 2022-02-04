package com.foxminded.dao.scheduledao;

import com.foxminded.schedule.Schedule;
import com.foxminded.teacher.Teacher;

import java.util.List;

public interface ScheduleDao {

    Schedule create(Schedule schedule);

    List<Schedule> findAll();

    List<Schedule> takeScheduleToTeacher(Teacher teacher);

    Schedule update(Schedule scheduleNew, Schedule scheduleOld);

    void delete(Schedule schedule);
}
