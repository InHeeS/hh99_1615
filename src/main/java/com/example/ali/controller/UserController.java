package com.example.ali.controller;

import com.example.ali.dto.SignupRequestDto;
import com.example.ali.dto.StringResponseDto;
import com.example.ali.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup-page")
    public String signupPage() {
        return "signup";
    }

    @ResponseBody
    @PostMapping("/signup")
    public StringResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }
}
