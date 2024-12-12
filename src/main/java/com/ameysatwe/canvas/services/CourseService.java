package com.ameysatwe.canvas.services;

import com.ameysatwe.canvas.DAO.CourseDAO;
import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDAO courseDAO;

    public List<Course> getCoursesForStudent(String studentEmail) {
//        return courseDAO.getCoursesByStudentEmail(studentEmail);
        return new ArrayList<>();
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    public List<Course> getAllCoursesByInstructor(User user){
        return courseDAO.findByInstructor(user);
    }

    public void addNewCourse(Course course){
        courseDAO.save(course);
    }

    public Course getCourseById(Long id){
        return courseDAO.findById(id);
    }

    public void updateCourse(Course course){
        courseDAO.update(course);
    }

    public void addAssignment(Course course, Assignment assignment){
        course.addAssignment(assignment);
        courseDAO.update(course);
    }
}
