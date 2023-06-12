package com.test.todo.service;

import com.test.todo.util.ResultMessage;
import com.test.todo.dto.AuthenticationDTO;

public interface UserService {
    ResultMessage<?> register(AuthenticationDTO dto);
    ResultMessage<?> login(AuthenticationDTO dto);
}
