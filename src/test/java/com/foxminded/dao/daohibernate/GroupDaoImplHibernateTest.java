package com.foxminded.dao.daohibernate;

import com.foxminded.dao.GroupDao;
import com.foxminded.model.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("Hibernate")
@Import(GroupDaoImplHibernate.class)
@DataJpaTest
class GroupDaoImplHibernateTest {

    @Autowired
    private GroupDao groupDao;

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
        expectedList.add(new Group("GE-22",1));
        expectedList.add(new Group("DT-12",2));
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
        expectedList.add(new Group("GE-22",1));
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }
}