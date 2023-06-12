package com.test.todo.service;

import com.test.todo.util.ResultMessage;
import com.test.todo.dao.UserRepository;
import com.test.todo.dto.AuthenticationDTO;
import com.test.todo.entity.UserBean;
import com.test.todo.enums.UserRoleEnum;
import com.test.todo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.test.todo.util.ResultMessage.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResultMessage<?> register(AuthenticationDTO dto) {
        if(userRepository.findByUsername(dto.getUsername()).isPresent()){
            return new ResultMessage<>(false, MESSAGE_USER_EXIST);
        }
        UserBean user = UserBean.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRoleEnum.valueOf(dto.getRole()))
                .build();
        userRepository.save(user);
        return new ResultMessage<>(true, MESSAGE_USER_REGISTER, jwtService.generateToken(user));
    }

    public ResultMessage<?> login(AuthenticationDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Optional<UserBean> user = userRepository.findByUsername(request.getUsername());
            return new ResultMessage<>(true, MESSAGE_USER_LOGIN_SUCCESS, user.map(jwtService::generateToken).orElse(null));
        } catch (AuthenticationException e) {
            return new ResultMessage<>(false, MESSAGE_USER_LOGIN_FAILED, null);
        }
    }
}
