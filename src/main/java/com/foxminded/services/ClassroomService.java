package com.foxminded.services;

import com.foxminded.model.Classroom;
import com.foxminded.dao.ClassroomDaoImpl;
import com.foxminded.dto.ClassroomDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomDaoImpl classroomDao;

    @Autowired
    private ModelMapper modelMapper;

    public ClassroomDTO create(Classroom classroom) {
        return mapping(classroomDao.create(classroom));
    }

    public List<ClassroomDTO> findAll() {
        return classroomDao.findAll().stream()
                .map(p -> mapping(p))
                .collect(Collectors.toList());
    }

    public ClassroomDTO update(Classroom classroomNew, Classroom classroomOld) {
        return mapping(classroomDao.update(classroomNew, classroomOld));
    }

    public void delete(Classroom classroom) {
        classroomDao.delete(classroom);
    }

    private ClassroomDTO mapping(Classroom classroom) {
        return modelMapper.map(classroom, ClassroomDTO.class);
    }
}
