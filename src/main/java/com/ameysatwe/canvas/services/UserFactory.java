package com.ameysatwe.canvas.services;

import com.ameysatwe.canvas.models.Instructor;
import com.ameysatwe.canvas.models.Student;
import com.ameysatwe.canvas.models.TA;
import com.ameysatwe.canvas.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    public User createUser(String role){
        System.out.println(role);
        return switch (role.toUpperCase()){
            case "STUDENT"-> new Student();
            case "INSTRUCTOR" -> new Instructor();
            case "TA" -> new TA();
            default -> throw new IllegalArgumentException("Invalid role");
        };
    }
}
