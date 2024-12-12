package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.Instructor;
import com.ameysatwe.canvas.models.User;
import com.ameysatwe.canvas.services.CourseService;
import com.ameysatwe.canvas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/instructor")
public class InstructorController {



    private final CourseService courseService;
    private final UserService userService;
    public InstructorController(CourseService courseService, UserService userService){
        this.courseService = courseService;
        this.userService = userService;
    }


    @GetMapping("/dashboard")
    public String showDashBoard(){
        return "instructor/dashboard";
    }
    @GetMapping("/add-course")
    public String showAddCourseForm(Model model){
        model.addAttribute("course",new Course());
        model.addAttribute("approvedTAs", userService.getApprovedTAs());
        return "instructor/add-course";
    }

    @PostMapping("/add-course")
    public String addCourse(@ModelAttribute("course") Course course, Authentication authentication){

//        User loggedInInstructor = (User) authentication.getPrincipal();
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
        Long id = user.get().getId();

        System.out.println(id);

        model.addAttribute("courses",courseService.getAllCoursesByInstructor(id));
        return "instructor/list-courses";
    }
}
