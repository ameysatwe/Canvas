package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.Assignment;
import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.Enrollment;
import com.ameysatwe.canvas.models.User;
import com.ameysatwe.canvas.services.AssignmentService;
import com.ameysatwe.canvas.services.CourseService;
import com.ameysatwe.canvas.services.EnrollmentService;
import com.ameysatwe.canvas.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    private final AssignmentService assignmentService;

    public StudentController(UserService userService, CourseService courseService, EnrollmentService enrollmentService,AssignmentService assignmentService) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.assignmentService = assignmentService;
    }

    @GetMapping("/student/dashboard")
    public String getStudentDashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email).isPresent() ? userService.getUserByEmail(email).get() : null;
        assert user != null;
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForUser(user.getId());
        model.addAttribute("enrollments", enrollments);
        return "student/dashboard";
    }

    @GetMapping("/student/register")
    public String getCourseRegistrationPage(Model model) {
        List<Course> allCourses = courseService.getAllCourses();
        model.addAttribute("courses", allCourses);
        return "student/registration";
    }

    @PostMapping("/student/register")
    public String registerCourses(@RequestParam("courseIds") List<Long> courseIds, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email).isPresent() ? userService.getUserByEmail(email).get() : null;

        for (Long courseId : courseIds) {
            Course course = courseService.getCourseById(courseId);
            assert user != null;
            enrollmentService.enrollUserInCourse(user, course);
        }

        return "redirect:/student/dashboard";
    }

    @GetMapping("/student/course/{id}")
    public String getCourseDetails(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "student/course-details";
    }

    @GetMapping("/student/course/{id}/assignments")
    public String viewAssignments(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        List<Assignment> assignments = assignmentService.getAssignmentsByCourseId(course);
        model.addAttribute("course", course);
        model.addAttribute("assignments", assignments);
        return "student/assignments";
    }


}
