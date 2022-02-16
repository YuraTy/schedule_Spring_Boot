package com.foxminded.dao.groupdao;

import com.foxminded.dao.GroupDaoImpl;
import com.foxminded.dao.testconfig.TestConfig;
import com.foxminded.model.Group;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = GroupDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
class GroupDaoImplTest {

    @Autowired
    private GroupDaoImpl groupDao;

    @AfterEach
    void deleteDate() {
        groupDao.findAll().stream()
                .peek(p -> groupDao.delete(p))
                .collect(Collectors.toList());
    }

    @Test
    void create() {
        groupDao.create(new Group("GE-22"));
        String expectedName = "GE-22";
        String actualName = groupDao.findAll().get(0).getNameGroup();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void findAll() {
        groupDao.create(new Group("GE-22"));
        groupDao.create(new Group("DT-12"));
        List<Group> expectedList = new ArrayList<>();
        expectedList.add(new Group("GE-22"));
        expectedList.add(new Group("DT-12"));
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        groupDao.create(new Group("GE-22"));
        groupDao.create(new Group("DT-12"));
        groupDao.update(new Group("DT-13"), new Group("DT-12"));
        groupDao.create(new Group("DT-14"));
        List<Group> expectedList = new ArrayList<>();
        expectedList.add(new Group("GE-22"));
        expectedList.add(new Group("DT-13"));
        expectedList.add(new Group("DT-14"));
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void delete() {
        groupDao.create(new Group("GE-22"));
        groupDao.create(new Group("DT-12"));
        groupDao.delete(new Group("DT-12"));
        List<Group> expectedList = new ArrayList<>();
        expectedList.add(new Group("GE-22"));
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}