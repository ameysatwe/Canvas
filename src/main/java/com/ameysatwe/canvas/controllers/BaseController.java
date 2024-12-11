package com.ameysatwe.canvas.controllers;

import com.ameysatwe.canvas.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {

    @GetMapping("/login")
    public String getMapping(){
        return "login";
    }

    @GetMapping("/home")
    public ModelAndView GetMapping(User user, ModelMap modelMap){
        user.setEmail("asatwe@gmail.com");
        ModelAndView modelAndView = new ModelAndView("hello-world");
        modelAndView.addObject("user",user);
        return modelAndView;
    }
}
