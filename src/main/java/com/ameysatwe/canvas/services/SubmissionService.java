package com.ameysatwe.canvas.services;

import com.ameysatwe.canvas.DAO.SubmissionDAO;
import com.ameysatwe.canvas.DAO.SubmissionDAO;
import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Submission;
import com.ameysatwe.canvas.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

    private final SubmissionDAO submissionDao;

    @Autowired
    public SubmissionService(SubmissionDAO submissionDao) {
        this.submissionDao = submissionDao;
    }

    // Check if a student has already submitted the assignment
    public boolean existsByAssignmentAndStudent(Assignment assignment, User student) {
        return submissionDao.existsByAssignmentAndStudent(assignment, student);
    }

    // Check if a student has already submitted an assignment by ID
    public boolean existsByAssignmentAndStudentId(Long assignmentId, Long studentId) {
        return submissionDao.existsByAssignmentAndStudentId(assignmentId, studentId);
    }

    // Save a new submission
    public void save(Submission submission) {
        submissionDao.save(submission);
    }
}
