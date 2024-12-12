//package com.ameysatwe.canvas.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/error")
//public class ErrorController {
//
//
//    @GetMapping
//    @ExceptionHandler(Exception.class)
//    public String showError(Exception ex, Model model){
//        model.addAttribute("exception",ex);
//        return "error";
//    }
//}
