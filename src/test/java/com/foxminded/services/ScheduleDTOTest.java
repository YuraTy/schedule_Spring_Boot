package com.foxminded.services;

import com.foxminded.classroom.Classroom;
import com.foxminded.dao.testconfig.TestConfig;
import com.foxminded.mapperdto.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = MapperDTO.class),
        @ContextConfiguration(classes = ScheduleDTO.class),
        @ContextConfiguration(classes = Classroom.class)
})
@ExtendWith(SpringExtension.class)
class ScheduleDTOTest {

    @Autowired
    Classroom classroom;

    @Autowired
    ScheduleDTO scheduleDto;

    @Autowired
    MapperDTO mapperDTO;

    @Test
    void tests() {
        classroom = new Classroom(22,11);
        Classroom classroomw = mapperDTO.mapper().map(classroom, Classroom.class);
        Assertions.assertEquals(classroom.getNumberClassroom(), classroomw.getNumberClassroom());
    }

}