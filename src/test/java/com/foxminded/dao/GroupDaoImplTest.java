package com.foxminded.dao;

import com.foxminded.testconfig.TestConfig;
import com.foxminded.model.Group;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = GroupDaoImpl.class)
})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

class GroupDaoImplTest {

    @Autowired
    private GroupDaoImpl groupDao;

    @AfterEach
    void deleteDate() {
        groupDao.findAll()
                .forEach(p -> groupDao.delete(p));
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