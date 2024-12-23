package com.ameysatwe.canvas.DAO;

import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.Submission;
import com.ameysatwe.canvas.models.User;

import java.util.List;

public interface SubmissionDAO {
    boolean existsByAssignmentAndStudent(Assignment assignment, User student);

    boolean existsByAssignmentAndStudentId(Long assignmentId, Long studentId);

    void save(Submission submission);

    List<Submission> findByAssignmentId(Long assignmentId);

    Submission findBySubmissionId(Long id);

    void update(Submission submission);

    List<Submission> getSubmissionsByStudentAndCourse(User student, Long courseId);

    List<Submission> getSubmissionsByCourse(Course course);
}
