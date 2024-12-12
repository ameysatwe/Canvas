package com.ameysatwe.canvas.DAO.impl;

import com.ameysatwe.canvas.DAO.AssignmentDAO;
import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssignmentDAOImpl implements AssignmentDAO {


    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void save(Assignment assignment) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try (session) {
            transaction = session.beginTransaction();
            Course course = session.find(Course.class, assignment.getCourse().getId());
            Assignment newAssignment = new Assignment();
            newAssignment.setTitle(assignment.getTitle());
            newAssignment.setDescription(assignment.getDescription());
            newAssignment.setCourse(course);
            newAssignment.setDueDate(assignment.getDueDate());
            session.persist(newAssignment);
            transaction.commit();
        } catch (Exception e) {
//            System.out.println(e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

    }

    @Override
    public List<Assignment> getAssignmentsByCourseId(Course course) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Assignment WHERE course = :id", Assignment.class)
                    .setParameter("id", course)
                    .getResultList();
        }
    }
}
