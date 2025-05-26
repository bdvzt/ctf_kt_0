package com.example.ctf_kt_0.controller;

import com.example.ctf_kt_0.dto.ErrorResponseDTO;
import com.example.ctf_kt_0.dto.FlagRequestDTO;
import com.example.ctf_kt_0.entity.User;
import com.example.ctf_kt_0.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flag")
@RequiredArgsConstructor
@Slf4j
public class FlagController {

    private final UserRepository userRepository;

    @PostMapping("/check")
    public ResponseEntity<?> checkFlag(@RequestBody FlagRequestDTO request) {
        try {
            String flag = request.getFlag();
            if (flag == null || flag.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ErrorResponseDTO(400, "Флаг не должен быть пустым"));
            }

            flag = flag.trim();

            User adminUser = userRepository.findByUsername("adminchik228").orElse(null);
            if (adminUser == null || adminUser.getFlag() == null) {
                return ResponseEntity.status(500).body(new ErrorResponseDTO(500, "Флаг не настроен"));
            }

            if (flag.equals(adminUser.getFlag())) {
                return ResponseEntity.ok("даблъю даблъю йоу эщкере поздр");
            } else {
                return ResponseEntity.status(400).body(new ErrorResponseDTO(400, "не даблъю"));
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponseDTO(500, "Ошибка при проверке флага"));
        }
    }
}
