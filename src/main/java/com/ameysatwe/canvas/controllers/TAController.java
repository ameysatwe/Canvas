package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.*;
import com.ameysatwe.canvas.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ta")
public class TAController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserService userService;



    @GetMapping("/dashboard")
    public String dashboard() {
        return "ta/dashboard";
    }

    @GetMapping("/courses")
    public String viewCourses(Model model, Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName()).get();
        List<Course> taCourses = courseService.getCoursesForTA(user);
        model.addAttribute("courses", taCourses);
        return "ta/courses";
    }

    @GetMapping("/courses/{courseId}/assignments")
    public String viewAssignments(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId);
        List<Assignment> assignments = assignmentService.getAssignmentsByCourseId(course);
        model.addAttribute("assignments", assignments);
        model.addAttribute("courseId", courseId);
        return "ta/assignments";
    }
    @GetMapping("/courses/{courseId}/assignments/{assignmentId}/submissions")
    public String viewSubmissions(@PathVariable Long assignmentId, Model model) {
        List<Submission> submissions = submissionService.findByAssignmentId(assignmentId);
        model.addAttribute("submissions", submissions);
        model.addAttribute("assignmentId", assignmentId);
        return "ta/submissions";
    }
//
//    @GetMapping("/submissions/{submissionId}/view")
//    public String viewSubmission(@PathVariable Long submissionId, Model model) {
//        Submission submission = submissionService.findById(submissionId);
//        model.addAttribute("submission", submission);
//        return "ta-view-submission";
//    }
//
//    @GetMapping("/submissions/{submissionId}/grade")
//    public String gradeSubmission(@PathVariable Long submissionId, Model model) {
//        Submission submission = submissionService.findById(submissionId);
//        model.addAttribute("submission", submission);
//        return "ta-grade-submission";
//    }
}
