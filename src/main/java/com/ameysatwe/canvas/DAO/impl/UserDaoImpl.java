package com.ameysatwe.canvas.DAO.impl;


import com.ameysatwe.canvas.DAO.UserDao;
import com.ameysatwe.canvas.models.Role;
import com.ameysatwe.canvas.models.Student;
import com.ameysatwe.canvas.models.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> findByRole(Role role) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE role = :role", User.class)
                    .setParameter("role", role)
                    .getResultList();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }

    @Override
    public List<User> findAllTAs() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE role = 'TA'", User.class).getResultList();
        }
    }

    @Override
    public void save(Student user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
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
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<User> findAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }
    @Override
    public List<User> findUnapprovedUsers() {
        return sessionFactory.openSession().createQuery("SELECT u FROM User u WHERE role != 'ADMIN'", User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void approveUser(Long userId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                user.setApproved(true);
                session.merge(user);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
