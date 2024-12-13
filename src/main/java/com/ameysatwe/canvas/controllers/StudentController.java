package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.*;
import com.ameysatwe.canvas.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    private final AssignmentService assignmentService;

    private final FileService fileService;

    private final SubmissionService submissionService;

    public StudentController(UserService userService,
                             CourseService courseService,
                             EnrollmentService enrollmentService,
                             AssignmentService assignmentService,
                             FileService fileService,
                             SubmissionService submissionService) {

        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.assignmentService = assignmentService;
        this.fileService = fileService;
        this.submissionService = submissionService;
    }

    @GetMapping("/dashboard")
    public String getStudentDashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email).isPresent() ? userService.getUserByEmail(email).get() : null;
        assert user != null;
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForUser(user.getId());
        model.addAttribute("enrollments", enrollments);
        return "student/dashboard";
    }

    @GetMapping("/register")
    public String getCourseRegistrationPage(Model model,Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName()).get();
        List<Course> allCourses = courseService.getAvailableCoursesForUser(user);
        model.addAttribute("courses", allCourses);
        return "student/registration";
    }

    @PostMapping("/register")
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

    @GetMapping("/course/{id}")
    public String getCourseDetails(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "student/course-details";
    }

    @GetMapping("/course/{id}/assignments")
    public String viewAssignments(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        List<Assignment> assignments = assignmentService.getAssignmentsByCourseId(course);
        model.addAttribute("course", course);
        model.addAttribute("assignments", assignments);
        return "student/assignments";
    }

    @GetMapping("/course/{courseId}/assignment/{id}/submit")
    public String showAssignmentSubmissionPage(@PathVariable Long courseId,@PathVariable Long id, Model model, Authentication authentication) {
        Assignment assignment = assignmentService.findById(id);
        String email = authentication.getName();
        User user = userService.getUserByEmail(email).isPresent() ? userService.getUserByEmail(email).get() : null;
        boolean isSubmitted = submissionService.existsByAssignmentAndStudent(assignment, user);
        model.addAttribute("courseId",courseId);
        model.addAttribute("assignment", assignment);
        model.addAttribute("isSubmitted", isSubmitted);
        return "student/submit-assignment";  // The submission template
    }
    @PostMapping("/course/{courseId}/assignment/{id}/submit")
    public String submitAssignment(@PathVariable Long id,
                                   @PathVariable Long courseId,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam(value = "comments", required = false) String comments,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        try {
            String fileUrl = fileService.storeFile(file);
            String email = authentication.getName();
            User user = userService.getUserByEmail(email).isPresent() ? userService.getUserByEmail(email).get() : null;
            Submission submission = new Submission();
            submission.setAssignment(assignmentService.findById(id));
            submission.setStudent(user);
            submission.setFileUrl(fileUrl);
            submission.setSubmittedAt(LocalDateTime.now());
            submission.setGrade(null); // No grade initially
            submission.setGrader(null); // get TA from the course
            submissionService.save(submission);
            return "redirect:/student/course/{courseId}";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to submit assignment: " + e.getMessage());
            return "redirect:/student/assignment/{id}/submit";
        }
    }

    @GetMapping("/course/{id}/submissions")
    public String viewSubmissionsForCourse(@PathVariable Long id, Model model, Authentication authentication) {
        Course course = courseService.getCourseById(id);
        String email = authentication.getName();
        User student = userService.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        List<Submission> submissions = submissionService.getSubmissionsByStudentAndCourse(student, id);
        model.addAttribute("course", course);
        model.addAttribute("submissions", submissions);
        return "student/course-submissions"; // New template for course-specific submissions
    }


}
