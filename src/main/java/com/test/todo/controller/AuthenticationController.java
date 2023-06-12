package com.test.todo.controller;

import com.test.todo.util.APIValidation;
import com.test.todo.util.ResultMessage;
import com.test.todo.service.UserService;
import com.test.todo.dto.AuthenticationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResultMessage<?> register(@RequestBody AuthenticationDTO dto){
        ResultMessage<?> validationError = APIValidation.validateAuthRegister(dto);
        return (validationError != null) ? validationError : userService.register(dto);
    }

    @PostMapping("/login")
    public ResultMessage<?> login(@RequestBody AuthenticationDTO dto){
        ResultMessage<?> validationError = APIValidation.validateAuthLogin(dto);
        return (validationError != null) ? validationError : userService.login(dto);
    }
}