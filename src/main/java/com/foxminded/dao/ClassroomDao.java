package com.foxminded.dao;

import com.foxminded.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomDao extends JpaRepository<Classroom,Long> {
    Classroom findByNumberClassroom(Integer numberClassroom);
}
