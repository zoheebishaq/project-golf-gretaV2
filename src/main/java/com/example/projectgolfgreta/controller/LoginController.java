package com.example.projectgolfgreta.controller;

import com.example.projectgolfgreta.service.JpaUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private JpaUserService userService;

    @Autowired
    public LoginController(JpaUserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    //    @GetMapping("/home")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/admin/form")
    public String admin() {
        return "inscription";
    }

    @PostMapping("admin/form")
    public String postAdmin(Model model,
                            @RequestParam String login,
                            @RequestParam String email,
                            @RequestParam String pass,
                            @RequestParam String passConfirm,
                            @RequestParam Long role) {
        this.userService.addUser(login, email, pass, role);
        return "redirect:/";
    }
}
