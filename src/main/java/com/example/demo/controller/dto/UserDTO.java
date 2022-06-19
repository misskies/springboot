package com.example.demo.controller.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String token;
    private String avatarUrl;
}
