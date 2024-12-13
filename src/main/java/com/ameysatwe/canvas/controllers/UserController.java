package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.*;
import com.ameysatwe.canvas.services.UserFactory;
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
    private UserFactory userFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User formUser) {
        Role role = formUser.getRole();
        User user = userFactory.createUser(role.toString());

        user.setEmail(formUser.getEmail());
        user.setPassword(passwordEncoder.encode(formUser.getPassword()));
        user.setName(formUser.getName());
        user.setRole(formUser.getRole());


        if (user instanceof Student student) {
            userService.registerUser(student);
        } else if (user instanceof Instructor instructor) {
            userService.registerInstructor(instructor);
        }else  if (user instanceof TA ta){
            userService.registerTA(ta);
        }

        return "redirect:/users/all";
    }

    @GetMapping("/register/instructor")
    public String showRegistrationFormForInstructor(Model model) {
        model.addAttribute("user", new Instructor());
        return "register";
    }

    @PostMapping("/register/instructor")
    public String registerUser(@ModelAttribute("user") Instructor user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerInstructor(user);
        return "redirect:/users/all";
    }


    @GetMapping("/all")
    public String listAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users-list";
    }
}
