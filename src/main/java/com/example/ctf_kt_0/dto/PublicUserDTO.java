package com.example.ctf_kt_0.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicUserDTO {
    private Long id;
    private String username;
    private String role;
}
