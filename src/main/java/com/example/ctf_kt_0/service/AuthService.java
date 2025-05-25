package com.example.ctf_kt_0.service;


import com.example.ctf_kt_0.dto.AuthRequest;
import com.example.ctf_kt_0.dto.RegisterRequest;
import com.example.ctf_kt_0.entity.User;
import com.example.ctf_kt_0.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public ResponseEntity<String> login(AuthRequest request, HttpSession session) {
        return userRepository.findByUsername(request.getUsername())
                .filter(u -> u.getPassword().equals(request.getPassword()))
                .map(user -> {
                    session.setAttribute("userId", user.getId());
                    return ResponseEntity.ok("Logged in as " + user.getUsername());
                })
                .orElse(ResponseEntity.status(400).body("Invalid credentials"));
    }

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    public String register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        userRepository.save(user);
        return "ok";
    }
}
