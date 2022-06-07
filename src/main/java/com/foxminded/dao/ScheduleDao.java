package com.foxminded.dao;

import com.foxminded.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleDao extends JpaRepository<Schedule,Long> {

    List<Schedule> findByTeacherId(Integer teacherId);
}
