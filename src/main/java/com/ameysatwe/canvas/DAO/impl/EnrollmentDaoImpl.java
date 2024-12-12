package com.ameysatwe.canvas.DAO.impl;

import com.ameysatwe.canvas.DAO.EnrollmentDAO;
import com.ameysatwe.canvas.models.Enrollment;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EnrollmentDaoImpl implements EnrollmentDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Enrollment> findByCourse(Long courseId) {
        return sessionFactory.openSession().createNamedQuery("Enrollment.findByCourse",Enrollment.class)
                .setParameter("courseId",courseId)
                .getResultList();
    }

    @Override
    public List<Enrollment> findByUser(Long userId) {
        return sessionFactory.openSession().createNamedQuery("Enrollment.findByUser",Enrollment.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public Long countByCourse(Long courseId) {
        return null;
    }

    @Override
    public void save(Enrollment enrollment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(enrollment);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existsByUserIdAndCourseId(Long userId, Long courseId) {
        return !sessionFactory.openSession().createQuery(
                        "SELECT e FROM Enrollment e WHERE e.user.id = :userId AND e.course.id = :courseId")
                .setParameter("userId", userId)
                .setParameter("courseId", courseId)
                .getResultList()
                .isEmpty();
    }
}
