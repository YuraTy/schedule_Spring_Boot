package com.foxminded.dao.daohibernate;

import com.foxminded.dao.TeacherDao;
import com.foxminded.model.Teacher;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
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
        entityManager.detach(teacherOld);
        entityManager.merge(teacherNew);
        return teacherNew;
    }

    @Override
    public void delete(Teacher teacher) {
        Teacher book = entityManager.find(Teacher.class,teacher.getId());
        entityManager.remove(book);
    }
}
