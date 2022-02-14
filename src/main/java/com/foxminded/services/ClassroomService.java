package com.foxminded.services;

import com.foxminded.classroom.Classroom;
import com.foxminded.dao.ClassroomDaoImpl;
import com.foxminded.objectdto.ClassroomDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomDaoImpl classroomDao;

    @Autowired
    private ModelMapper modelMapper;

    public Classroom create(Classroom classroom) {
        return classroomDao.create(classroom);
    }

    public List<Classroom> findAll() {
        return classroomDao.findAll();
    }

    public Classroom update(Classroom classroomNew, Classroom classroomOld) {
        return classroomDao.update(classroomNew,classroomOld);
    }

    public void delete(Classroom classroom) {
        classroomDao.delete(classroom);
    }

    public ClassroomDTO mapping(Classroom classroom) {
        return modelMapper.map(classroom, ClassroomDTO.class);
    }
}
