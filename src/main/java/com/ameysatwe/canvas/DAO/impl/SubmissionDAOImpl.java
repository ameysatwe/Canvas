package com.ameysatwe.canvas.DAO.impl;

import com.ameysatwe.canvas.DAO.SubmissionDAO;
import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Submission;
import com.ameysatwe.canvas.models.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubmissionDAOImpl implements SubmissionDAO {

    private final SessionFactory sessionFactory;


    public SubmissionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Check if a student has already submitted an assignment using Criteria API
    public boolean existsByAssignmentAndStudent(Assignment assignment, User student) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Submission> root = cq.from(Submission.class);

        // Add conditions to the query
        Predicate assignmentPredicate = cb.equal(root.get("assignment"), assignment);
        Predicate studentPredicate = cb.equal(root.get("student"), student);

        cq.select(cb.count(root)).where(cb.and(assignmentPredicate, studentPredicate));

        Query<Long> query = session.createQuery(cq);
        Long count = query.getSingleResult();
        session.close();

        return count != null && count > 0;
    }

    // Check if a student has already submitted an assignment by IDs using CriteriaQuery
    public boolean existsByAssignmentAndStudentId(Long assignmentId, Long studentId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Submission> root = cq.from(Submission.class);

        // Add conditions to the query
        Predicate assignmentPredicate = cb.equal(root.get("assignment").get("id"), assignmentId);
        Predicate studentPredicate = cb.equal(root.get("student").get("id"), studentId);

        cq.select(cb.count(root)).where(cb.and(assignmentPredicate, studentPredicate));

        Query<Long> query = session.createQuery(cq);
        Long count = query.getSingleResult();
        session.close();

        return count != null && count > 0;
    }

    // Save or update a submission
    public void save(Submission submission) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(submission);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Submission> findByAssignmentId(Long assignmentId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Submission> cq = cb.createQuery(Submission.class);
        Root<Submission> root = cq.from(Submission.class);

        // Add conditions to the query
        Predicate assignmentPredicate = cb.equal(root.get("assignment").get("id"), assignmentId);

        cq.select(root).where(assignmentPredicate);

        Query<Submission> query = session.createQuery(cq);
        List<Submission> submissions = query.getResultList();
        session.close();

        return submissions;
    }
}
