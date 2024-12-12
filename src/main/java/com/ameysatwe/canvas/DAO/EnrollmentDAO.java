package com.ameysatwe.canvas.DAO;

import com.ameysatwe.canvas.models.Enrollment;

import java.util.List;

public interface EnrollmentDAO {
    List<Enrollment> findByCourse(Long courseId);
    List<Enrollment> findByUser(Long userId);
    Long countByCourse(Long courseId);
    void save(Enrollment enrollment);
    void delete(Long id);

    boolean existsByUserIdAndCourseId(Long userId,Long courseId);
}