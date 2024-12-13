package com.ameysatwe.canvas.DAO.impl;

import com.ameysatwe.canvas.DAO.CourseDAO;
import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDaoImpl implements CourseDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Course> findByInstructor(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Course WHERE instructor = :instructor", Course.class)
                    .setParameter("instructor", user)
                    .getResultList();
        }

    }

    @Override
    public List<Course> findByTA(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Course WHERE ta = :ta", Course.class)
                    .setParameter("ta", user)
                    .getResultList();
        }
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public void save(Course course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(course);
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
    public Course findById(Long id) {
        System.out.println(sessionFactory.hashCode());
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Course WHERE id = :id", Course.class)
                    .setParameter("id",id)
                    .uniqueResult();
        }
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(Course course){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

    }

    @Override
    public List<Course> getAllCourses(){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Course", Course.class)
                    .getResultList();
        }
    }
    @Override
    public List<Course> getAvailableCoursesForUser(User user) {
        String hql = """
        SELECT c
        FROM Course c
        WHERE c.isPublished = true
          AND c.id NOT IN (
            SELECT e.course.id
            FROM Enrollment e
            WHERE e.user.id = :userId
          )
    """;
        return sessionFactory.openSession().createQuery(hql, Course.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

}
