package com.test.todo.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
}
