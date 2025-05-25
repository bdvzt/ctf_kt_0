package com.example.ctf_kt_0.service;

import com.example.ctf_kt_0.dto.PublicUserDTO;
import com.example.ctf_kt_0.dto.UserDTO;
import com.example.ctf_kt_0.entity.User;
import com.example.ctf_kt_0.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public User getCurrentUser(HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        return userRepository.findById(id).orElseThrow();
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public List<PublicUserDTO> searchUsers(String query) {
        String jpql = "SELECT u FROM User u WHERE u.username LIKE '%" + query + "%'";
        List<User> users = entityManager.createQuery(jpql, User.class).getResultList();

        return users.stream()
                .map(u -> new PublicUserDTO(u.getId(), u.getUsername(), u.getRole()))
                .toList();
    }
}
