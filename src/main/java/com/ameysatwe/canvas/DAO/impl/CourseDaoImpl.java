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
    public List<Course> findByTA(Long taId) {
        return null;
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
}
