package com.ameysatwe.canvas.DAO;

import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Submission;
import com.ameysatwe.canvas.models.User;

public interface SubmissionDAO {
    boolean existsByAssignmentAndStudent(Assignment assignment, User student);

    boolean existsByAssignmentAndStudentId(Long assignmentId, Long studentId);

    void save(Submission submission);
}
