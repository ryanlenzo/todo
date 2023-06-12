package com.test.todo.dto;

import lombok.Data;

@Data
public class AuthenticationDTO {
    private String username;
    private String password;
    private String role;
}
