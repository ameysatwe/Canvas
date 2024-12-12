package com.ameysatwe.canvas.services;

import com.ameysatwe.canvas.DAO.AssignmentDAO;
import com.ameysatwe.canvas.DAO.CourseDAO;
import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.Enrollment;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentDAO assignmentDAO;

    @Autowired
    private SessionFactory sessionFactory;
//    private CourseService courseService;

    private CourseDAO courseDAO;

    public AssignmentService(AssignmentDAO assignmentDAO,CourseDAO courseDAO) {
        this.assignmentDAO = assignmentDAO;
        this.courseDAO = courseDAO;
    }


    public void saveAssignment(Assignment assignment, Long courseId) {
        // Explicitly fetch the course within the transactional context
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

    public List<Assignment> getAssignmentsByCourseId(Course course) {
        return assignmentDAO.getAssignmentsByCourseId(course);
    }
    // Other methods for assignment operations
}