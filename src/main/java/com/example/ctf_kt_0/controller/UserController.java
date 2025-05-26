package com.example.ctf_kt_0.controller;

import com.example.ctf_kt_0.dto.ErrorResponseDTO;
import com.example.ctf_kt_0.dto.PublicUserDTO;
import com.example.ctf_kt_0.dto.UserDTO;
import com.example.ctf_kt_0.entity.User;
import com.example.ctf_kt_0.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить текущего пользователя (требуется авторизация)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный запрос"),
            @ApiResponse(responseCode = "400", description = "Пользователь не авторизован или не найден")
    })
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        try {
            User user = userService.getCurrentUser(session);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(400, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ErrorResponseDTO(500, "Unexpected error"));
        }
    }

    @Operation(summary = "Получить информацию о пользователе по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "400", description = "Неверный ID или пользователь не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(400, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ErrorResponseDTO(500, "Unexpected error"));
        }
    }

    @Operation(summary = "Поиск пользователей по имени")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователи найдены"),
            @ApiResponse(responseCode = "400", description = "Пустой или некорректный запрос")
    })
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "query") String query) {
        try {
            return ResponseEntity.ok(userService.searchUsers(query));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(400, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ErrorResponseDTO(500, "Unexpected error"));
        }
    }
}

