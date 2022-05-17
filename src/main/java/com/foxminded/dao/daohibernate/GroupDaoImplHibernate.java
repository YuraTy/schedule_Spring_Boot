package com.foxminded.dao.daohibernate;

import com.foxminded.dao.GroupDao;
import com.foxminded.model.Group;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GroupDaoImplHibernate implements GroupDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Group create(Group group) {
        entityManager.persist(group);
        return group;
    }

    @Override
    public List<Group> findAll() {
        return entityManager.createQuery("from Group", Group.class).getResultList();
    }

    @Override
    public Group update(Group groupNew, Group groupOld) {
        entityManager.detach(groupOld);
        entityManager.merge(groupNew);
        return groupNew;
    }

    @Override
    public void delete(Group group) {
        Group book = entityManager.find(Group.class, group.getId());
        entityManager.remove(book);
    }
}
