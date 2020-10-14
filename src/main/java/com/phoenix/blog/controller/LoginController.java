//package com.blog.MyBlog.controller;
//
//import com.blog.MyBlog.dto.AuthenticationResponse;
//import com.blog.MyBlog.dto.LoginRequest;
//import com.blog.MyBlog.service.AuthService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//@AllArgsConstructor
//public class LoginController {
//    private AuthService authService;
//    @PostMapping("/login")
//    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
//        return authService.login(loginRequest);
//    }
//}
