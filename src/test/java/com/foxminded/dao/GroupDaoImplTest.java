package com.foxminded.dao;

import com.foxminded.model.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class GroupDaoImplTest {

    @Autowired
    private GroupDao groupDao;

    @Test
    void create() {
        groupDao.save(new Group("GE-22"));
        String expectedName = "GE-22";
        String actualName = groupDao.findAll().get(0).getNameGroup();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void findAll() {
        groupDao.save(new Group("GE-22"));
        groupDao.save(new Group("DT-12"));
        List<Group> expectedList = new ArrayList<>();
        expectedList.add(new Group("GE-22",1));
        expectedList.add(new Group("DT-12",2));
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update() {
        groupDao.save(new Group("GE-22"));
        groupDao.save(new Group("DT-12"));
        Group groupBook = groupDao.findByNameGroup("DT-12");
        groupBook.setNameGroup("DT-13");
        groupDao.save(groupBook);
        groupDao.save(new Group("DT-14"));
        List<Group> expectedList = new ArrayList<>();
        expectedList.add(new Group("GE-22"));
        expectedList.add(new Group("DT-13"));
        expectedList.add(new Group("DT-14"));
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void delete() {
        groupDao.save(new Group("GE-22"));
        groupDao.save(new Group("DT-12"));
        groupDao.delete(new Group("DT-12",2));
        List<Group> expectedList = new ArrayList<>();
        expectedList.add(new Group("GE-22",1));
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}