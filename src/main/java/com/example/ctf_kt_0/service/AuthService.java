package com.example.ctf_kt_0.service;

import com.example.ctf_kt_0.dto.AuthRequest;
import com.example.ctf_kt_0.dto.RegisterRequest;
import com.example.ctf_kt_0.entity.User;
import com.example.ctf_kt_0.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public String login(AuthRequest request, HttpSession session) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Username and password must not be empty");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    return new IllegalStateException("Invalid credentials");
                });

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalStateException("Invalid credentials");
        }

        session.setAttribute("userId", user.getId());
        return "Logged in as " + user.getUsername();
    }

    public String logout(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            session.invalidate();
            return "Logged out";
        } else {
            throw new IllegalStateException("No active session");
        }
    }

    public String register(RegisterRequest request) {
        if (request.getUsername() == null || request.getPassword() == null || request.getRole() == null) {
            throw new IllegalArgumentException("All fields (username, password, role) are required");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        userRepository.save(user);
        return "ok";
    }
}
