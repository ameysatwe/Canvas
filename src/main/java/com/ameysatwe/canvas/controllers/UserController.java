package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.Student;
import com.ameysatwe.canvas.models.User;
import com.ameysatwe.canvas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Student user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String listAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users-list";
    }
}
