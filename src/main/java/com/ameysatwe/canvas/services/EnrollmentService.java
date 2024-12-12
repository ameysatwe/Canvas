package com.ameysatwe.canvas.services;


import com.ameysatwe.canvas.DAO.EnrollmentDAO;
import org.springframework.stereotype.Service;
import com.ameysatwe.canvas.models.*;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentDAO enrollmentDao;

    public EnrollmentService(EnrollmentDAO enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }

    public void enrollUserInCourse(User user, Course course) {
        if (!enrollmentDao.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            Enrollment enrollment = new Enrollment();
            enrollment.setUser(user);
            enrollment.setCourse(course);
            enrollment.setStatus("ACTIVE");
            enrollmentDao.save(enrollment);
        }
    }

    public List<Enrollment> getEnrollmentsForUser(Long userId) {
        return enrollmentDao.findByUser(userId);
    }

    public List<Enrollment> getEnrollmentsByCourseId(Long courseId){
        return enrollmentDao.findByCourse(courseId);
    }
}
