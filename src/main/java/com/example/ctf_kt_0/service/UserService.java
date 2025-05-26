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
        if (id == null) {
            throw new IllegalStateException("Not authenticated");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalStateException("User not found");
                });
    }

    public UserDTO getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalStateException("User not found");
                });

        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public List<PublicUserDTO> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query must not be empty");
        }

        try {
            String sql = "SELECT * FROM users WHERE " + query;
            List<User> users = entityManager.createNativeQuery(sql, User.class).getResultList();

            return users.stream()
                    .map(u -> new PublicUserDTO(u.getId(), u.getUsername(), u.getRole()))
                    .toList();

        } catch (Exception e) {
            throw new IllegalStateException("Error while searching users", e);
        }
    }
}
