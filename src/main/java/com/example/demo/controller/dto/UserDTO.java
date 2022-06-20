package com.example.demo.controller.dto;

import com.example.demo.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String token;
    private String avatarUrl;
    private String role;
    private List<Menu> menus;
}
