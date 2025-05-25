package com.example.ctf_kt_0.dto;


import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
}