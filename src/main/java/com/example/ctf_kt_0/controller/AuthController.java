package com.example.ctf_kt_0.controller;

import com.example.ctf_kt_0.dto.AuthRequest;
import com.example.ctf_kt_0.dto.RegisterRequest;
import com.example.ctf_kt_0.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный вход"),
            @ApiResponse(responseCode = "400", description = "Неверные учетные данные или пустой логин/пароль")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request, HttpSession session) {
        String message = authService.login(request, session);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Logout")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный выход"),
            @ApiResponse(responseCode = "400", description = "Сессия отсутствует")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        String message = authService.logout(session);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Register")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Неверные данные или пользователь уже существует")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String message = authService.register(request);
        return ResponseEntity.ok(message);
    }
}
