package com.ameysatwe.canvas.controllers;


import com.ameysatwe.canvas.models.*;
import com.ameysatwe.canvas.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;


    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("allUsers", userService.findUnapprovedUsers());
        return "admin/admin-dashboard"; // View for the dashboard
    }

    @PostMapping("/approve/{id}")
    public String approveUser(@PathVariable("id") Long userId) {
        System.out.println("approve user");
        userService.approveUser(userId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/courses")
    public String listAllCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "admin/courses";
    }

    @GetMapping("/course/{id}/stats")
    public String getCourseStats(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(course.getId());
        List<Assignment> assignments = assignmentService.getAssignmentsByCourseId(course);
        List<Submission> submissions = submissionService.getSubmissionsByCourse(course);

        model.addAttribute("course", course);
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("assignments", assignments);
        model.addAttribute("submissions", submissions);

        return "admin/course-stats";
    }


}
