package com.example.ctf_kt_0.controller;

import com.example.ctf_kt_0.dto.AuthRequest;
import com.example.ctf_kt_0.dto.RegisterRequest;
import com.example.ctf_kt_0.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request, HttpSession session) {
        return authService.login(request, session);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        return authService.logout(session);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}