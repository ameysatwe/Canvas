package com.ameysatwe.canvas.services;

import com.ameysatwe.canvas.DAO.CourseDAO;
import com.ameysatwe.canvas.models.Course;
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
//        return courseDAO.findAllCourses();
//    return courseDAO.findByInstructor()
        return new ArrayList<>();
    }

    public List<Course> getAllCoursesByInstructor(Long ins_id){
        return courseDAO.findByInstructor(ins_id);
    }

    public void addNewCourse(Course course){
        courseDAO.save(course);
    }

}
