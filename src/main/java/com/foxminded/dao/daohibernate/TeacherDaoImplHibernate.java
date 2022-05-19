package com.foxminded.dao.daohibernate;

import com.foxminded.dao.TeacherDao;
import com.foxminded.model.Teacher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
@Profile("Hibernate")
public class TeacherDaoImplHibernate implements TeacherDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Teacher create(Teacher teacher) {
        entityManager.persist(teacher);
        return teacher;
    }

    @Override
    public List<Teacher> findAll() {
        return entityManager.createQuery("from Teacher",Teacher.class).getResultList();
    }

    @Override
    public Teacher update(Teacher teacherNew, Teacher teacherOld) {
        Teacher teacherBookOld = getTeacherByName(teacherOld);
        entityManager.detach(teacherBookOld);
        teacherNew.setId(teacherBookOld.getId());
        entityManager.merge(teacherNew);
        return teacherNew;
    }

    @Override
    public void delete(Teacher teacher) {
        Teacher book = getTeacherByName(teacher);
        entityManager.remove(book);
    }

    private Teacher getTeacherByName(Teacher teacher) {
        return entityManager.createNamedQuery("selectTeacherByName",Teacher.class)
                .setParameter(1,teacher.getFirstName())
                .setParameter(2,teacher.getLastName())
                .getResultList().get(0);
    }
}
