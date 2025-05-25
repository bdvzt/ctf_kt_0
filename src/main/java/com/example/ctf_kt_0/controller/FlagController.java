package com.example.ctf_kt_0.controller;

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
    public ResponseEntity<String> checkFlag(@RequestBody String submittedFlag) {
        try {
            String flag = submittedFlag.trim();

            if (flag.isEmpty()) {
                log.warn("Попытка отправки пустого флага");
                return ResponseEntity.badRequest().body("Флаг не должен быть пустым");
            }

            boolean flagExists = userRepository
                    .findAll()
                    .stream()
                    .anyMatch(user -> flag.equals(user.getFlag()));

            if (flagExists) {
                log.info("Флаг корректен: {}", flag);
                return ResponseEntity.ok("даблъю даблъю йоу эщкере поздр");
            } else {
                log.warn("Флаг не прошел проверку: {}", flag);
                return ResponseEntity.status(400).body("не даблъю");
            }

        } catch (Exception e) {
            log.error("Ошибка при проверке флага", e);
            return ResponseEntity.internalServerError().body("Произошла ошибка при проверке флага");
        }
    }
}
