package com.foxminded.dao;

import com.foxminded.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDao extends JpaRepository<Teacher,Long> {

    Teacher findByFirstNameAndLastName(String firstName, String lastName);
}
