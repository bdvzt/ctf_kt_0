package com.example.ctf_kt_0.controller;

import com.example.ctf_kt_0.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flag")
@RequiredArgsConstructor
public class FlagController {

    private final UserRepository userRepository;

    @PostMapping("/check")
    public ResponseEntity<String> checkFlag(@RequestBody String submittedFlag) {
        boolean flagExists = userRepository
                .findAll()
                .stream()
                .anyMatch(user -> submittedFlag.trim().equals(user.getFlag()));

        if (flagExists) {
            return ResponseEntity.ok("даблъю даблъю йоу эщкере поздр");
        } else {
            return ResponseEntity.status(400).body("не даблъю");
        }
    }
}
