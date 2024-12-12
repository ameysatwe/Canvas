package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.*;
import com.ameysatwe.canvas.services.CourseService;
import com.ameysatwe.canvas.services.EnrollmentService;
import com.ameysatwe.canvas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/instructor")
public class InstructorController {



    private final CourseService courseService;
    private final UserService userService;

    private final EnrollmentService enrollmentService;
    public InstructorController(CourseService courseService, UserService userService,EnrollmentService enrollmentService){
        this.courseService = courseService;
        this.userService = userService;
        this.enrollmentService = enrollmentService;
    }


    @GetMapping("/dashboard")
    public String showDashBoard(Authentication authentication,Model model){
        String username = authentication.getName();

        Optional<User> user = userService.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Instructor not found");
        }

        List<Course> courses = courseService.getAllCoursesByInstructor(user.get());
        model.addAttribute("courses",courses);
        return "instructor/dashboard";
    }

    @GetMapping("/course/{id}")
    public String manageCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "instructor/course-details";
    }

    @GetMapping("/course/{id}/enrollments")
    public String viewEnrollments(@PathVariable Long id, Model model) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(id);
        model.addAttribute("enrollments", enrollments);
        return "instructor/enrollments";

    }

    @GetMapping("/course/{id}/assignments")
    public String viewAssignments(@PathVariable Long id, Model model) {
        List<Assignment> assignments = new ArrayList<>();//assignmentService.getAssignmentsByCourseId(id);
        model.addAttribute("assignments", assignments);
        return "instructor/assignments";
    }


    @GetMapping("/add-course")
    public String showAddCourseForm(Model model){
        model.addAttribute("course",new Course());
        model.addAttribute("approvedTAs", userService.getApprovedTAs());
        return "instructor/add-course";
    }

    @PostMapping("/add-course")
    public String addCourse(@ModelAttribute("course") Course course, Authentication authentication){
        String username = authentication.getName();

        Optional<User> user = userService.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Instructor not found");
        }

        System.out.println(username);
        course.setTa(null);
        course.setInstructor(user.get());

        courseService.addNewCourse(course);

        return "redirect:list-courses";
    }

    @GetMapping("/list-courses")
    public String listCourses(Model model,Authentication authentication){
        String username = authentication.getName();

        Optional<User> user = userService.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Instructor not found");
        }

        model.addAttribute("courses",courseService.getAllCoursesByInstructor(user.get()));
        return "instructor/list-courses";
    }
    @PostMapping("/toggle-publish")
    @ResponseBody
    public Map<String, Object> togglePublish(@RequestParam("courseId") Long courseId) {
        Course course = courseService.getCourseById(courseId);
        Map<String, Object> response = new HashMap<>();

        if (course != null) {
            course.setPublished(!course.getPublished());
            courseService.updateCourse(course);
            response.put("success", true);
            response.put("published", course.getPublished());
        } else {
            response.put("success", false);
        }

        return response;
    }


}
