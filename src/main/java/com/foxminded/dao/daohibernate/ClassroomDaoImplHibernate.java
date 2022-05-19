package com.foxminded.dao.daohibernate;

import com.foxminded.dao.ClassroomDao;
import com.foxminded.model.Classroom;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
@Profile("Hibernate")
public class ClassroomDaoImplHibernate implements ClassroomDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Classroom create(Classroom classroom) {
        entityManager.persist(classroom);
        return classroom;
    }

    @Override
    public List<Classroom> findAll() {
        return entityManager.createQuery("from Classroom",Classroom.class).getResultList();
    }

    @Override
    public Classroom update(Classroom classroomNew, Classroom classroomOld) {
        Classroom classroomBookOld = getClassroomByNumber(classroomOld);
        entityManager.detach(classroomBookOld);
        classroomNew.setId(classroomBookOld.getId());
        entityManager.merge(classroomNew);
        return classroomNew;
    }

    @Override
    public void delete(Classroom classroom) {
        Classroom book = getClassroomByNumber(classroom);
        entityManager.remove(book);
    }

    private Classroom getClassroomByNumber (Classroom classroom) {
        return entityManager.createNamedQuery("selectClassroomByNumber",Classroom.class)
                .setParameter(1,classroom.getNumberClassroom())
                .getResultList().get(0);
    }

}
