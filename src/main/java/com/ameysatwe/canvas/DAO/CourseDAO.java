package com.ameysatwe.canvas.DAO;


import com.ameysatwe.canvas.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    List<Course> findByInstructor(Long instructorId);
    List<Course> findByTA(Long taId);
    Optional<Course> findByTitle(String title);
    void save(Course course);
    Optional<Course> findById(Long id);
    void deleteById(Long id);
}
