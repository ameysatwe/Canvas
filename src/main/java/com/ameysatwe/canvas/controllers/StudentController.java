package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.services.CourseService;
import com.ameysatwe.canvas.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentController {
    private final UserService userService;
    private final CourseService courseService;

    public StudentController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/student/dashboard")
    public String getStudentDashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<Course> registeredCourses = courseService.getCoursesForStudent(email);

        model.addAttribute("courses", registeredCourses);
        return "student/dashboard";
    }

    @GetMapping("/student/register")
    public String getCourseRegistrationPage(Model model) {
        List<Course> allCourses = courseService.getAllCourses();
        model.addAttribute("courses", allCourses);
        return "student/register";
    }
}
