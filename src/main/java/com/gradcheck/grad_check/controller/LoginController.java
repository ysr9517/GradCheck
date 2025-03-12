package com.gradcheck.grad_check.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "signIn";
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signUp"; // signUp.html을 반환합니다.
    }

//    @GetMapping("/main")
//    public String mainPage() {
//        return "mainPage"; // main.html 템플릿을 반환
//    }
}
