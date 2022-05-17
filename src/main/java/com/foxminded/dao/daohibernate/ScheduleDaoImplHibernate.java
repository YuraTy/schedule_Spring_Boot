package com.foxminded.dao.daohibernate;

import com.foxminded.dao.ScheduleDao;
import com.foxminded.model.Schedule;
import com.foxminded.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ScheduleDaoImplHibernate implements ScheduleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Schedule create(Schedule schedule) {
        entityManager.persist(schedule);
        return schedule;
    }

    @Override
    public List<Schedule> findAll() {
        return entityManager.createQuery("from Schedule",Schedule.class).getResultList();
    }

    @Override
    public List<Schedule> takeScheduleToTeacher(Teacher teacher) {
        return entityManager.createQuery("from Schedule schedule where schedule.teacher.id = :teacher_id",Schedule.class).setParameter("teacher_id",teacher.getId()).getResultList();
    }

    @Override
    public Schedule update(Schedule scheduleNew, Schedule scheduleOld) {
        entityManager.detach(scheduleOld);
        scheduleNew.setScheduleId(scheduleOld.getScheduleId());
        entityManager.merge(scheduleNew);
        return scheduleNew;
    }

    @Override
    public void delete(Schedule schedule) {
        Schedule book = entityManager.find(Schedule.class,schedule.getScheduleId());
        entityManager.remove(book);
    }
}
