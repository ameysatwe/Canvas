package com.ameysatwe.canvas.controllers;


import com.ameysatwe.canvas.models.Course;
import com.ameysatwe.canvas.models.User;
import com.ameysatwe.canvas.services.CourseService;
import com.ameysatwe.canvas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


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

}
