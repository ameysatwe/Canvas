package com.ameysatwe.canvas.DAO;


import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.User;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    List<Course> findByInstructor(User user);
    List<Course> findByTA(User TA);
    Optional<Course> findByTitle(String title);
    void save(Course course);
    Course findById(Long id);
    void deleteById(Long id);

    void update(Course course);

    List<Course> getAllCourses();


    List<Course> getAvailableCoursesForUser(User user);

}
