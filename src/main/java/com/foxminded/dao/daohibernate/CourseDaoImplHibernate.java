package com.foxminded.dao.daohibernate;

import com.foxminded.dao.CourseDao;
import com.foxminded.model.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CourseDaoImplHibernate implements CourseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Course create(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    public List<Course> findAll() {
        return entityManager.createQuery("from Course",Course.class).getResultList();
    }

    @Override
    public Course update(Course courseNew, Course courseOld) {
        Course courseBookOld = getCourseByName(courseOld);
        entityManager.detach(courseBookOld);
        courseNew.setId(courseBookOld.getId());
        entityManager.merge(courseNew);
        return courseNew;
    }

    @Override
    public void delete(Course course) {
        Course book = getCourseByName(course);
        entityManager.remove(book);
    }

    private Course getCourseByName(Course course) {
        return entityManager.createNamedQuery("selectCourseByName",Course.class)
                .setParameter(1,course.getNameCourse())
                .getResultList().get(0);
    }
}
