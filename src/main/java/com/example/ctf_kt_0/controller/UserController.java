package com.example.ctf_kt_0.controller;

import com.example.ctf_kt_0.dto.PublicUserDTO;
import com.example.ctf_kt_0.dto.UserDTO;
import com.example.ctf_kt_0.entity.User;
import com.example.ctf_kt_0.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public User me(HttpSession session) {
        return userService.getCurrentUser(session);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/search")
    public List<PublicUserDTO> search(@RequestParam String q) {
        return userService.searchUsers(q);
    }
}
