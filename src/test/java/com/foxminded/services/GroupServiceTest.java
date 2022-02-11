package com.foxminded.services;

import com.foxminded.dao.GroupDaoImpl;
import com.foxminded.dao.testconfig.TestConfig;
import com.foxminded.group.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextHierarchy({
        @ContextConfiguration(classes = TestConfig.class),
        @ContextConfiguration(classes = GroupDaoImpl.class),
        @ContextConfiguration(classes = GroupService.class)
})
@ExtendWith(SpringExtension.class)
class GroupServiceTest {

    @Mock
    GroupDaoImpl groupDao;

    @InjectMocks
    GroupService groupService;

    @Test
    void create() {
        groupService.create(new Group("WE-22"));
        Mockito.verify(groupDao).create(Mockito.any());
    }

    @Test
    void findAll() {
        groupService.findAll();
        Mockito.verify(groupDao).findAll();
    }

    @Test
    void update() {
        groupService.update(new Group("WE-22"),new Group("WE-23"));
        Mockito.verify(groupDao).update(Mockito.any(),Mockito.any());
    }

    @Test
    void delete() {
        groupService.delete(new Group("WE-22"));
        Mockito.verify(groupDao).delete(Mockito.any());
    }
}