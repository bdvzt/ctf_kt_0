package com.example.ctf_kt_0.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ctf_kt_0.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import com.example.ctf_kt_0.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @GetMapping("/me")
    public User me(HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        return userRepository.findById(id).orElseThrow();
    }

    // ⚠️ IDOR уязвимость
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    // ⚠️ SQL-инъекция
    @GetMapping("/search")
    public List<User> search(@RequestParam String q) {
        String jpql = "SELECT u FROM User u WHERE u.username LIKE '%" + q + "%'";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }
}

