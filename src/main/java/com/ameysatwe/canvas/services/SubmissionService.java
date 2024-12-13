package com.ameysatwe.canvas.services;

import com.ameysatwe.canvas.DAO.SubmissionDAO;
import com.ameysatwe.canvas.DAO.SubmissionDAO;
import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.Submission;
import com.ameysatwe.canvas.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionDAO submissionDao;

    @Autowired
    public SubmissionService(SubmissionDAO submissionDao) {
        this.submissionDao = submissionDao;
    }

    public boolean existsByAssignmentAndStudent(Assignment assignment, User student) {
        return submissionDao.existsByAssignmentAndStudent(assignment, student);
    }

    public boolean existsByAssignmentAndStudentId(Long assignmentId, Long studentId) {
        return submissionDao.existsByAssignmentAndStudentId(assignmentId, studentId);
    }

    // Save a new submission
    public void save(Submission submission) {
        submissionDao.save(submission);
    }

    public List<Submission> findByAssignmentId(Long assignmentId) {
        return submissionDao.findByAssignmentId(assignmentId);
    }

    public Submission findById(Long id) {
        return submissionDao.findBySubmissionId(id);
    }

    public void updateSubmission(Submission submission) {
        submissionDao.update(submission);
    }


    public List<Submission> getSubmissionsByStudentAndCourse(User student, Long courseId) {
        return submissionDao.getSubmissionsByStudentAndCourse(student, courseId);
    }

    public List<Submission> getSubmissionsByCourse(Course courseId) {
        return submissionDao.getSubmissionsByCourse(courseId);
    }
}
