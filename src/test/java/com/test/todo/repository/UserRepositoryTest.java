package com.test.todo.repository;

import com.test.todo.dao.UserRepository;
import com.test.todo.entity.UserBean;
import com.test.todo.enums.UserRoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_Save_ReturnUser(){
        UserBean user = UserBean.builder()
                .username("test1")
                .password("test1")
                .role(UserRoleEnum.ADMIN)
                .build();

        UserBean savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() > 0);
    }

    @Test
    public void UserRepository_FindAll_ReturnMoreThanOneUser(){
        UserBean user1 = UserBean.builder()
                .username("test1")
                .password("test1")
                .role(UserRoleEnum.ADMIN)
                .build();
        UserBean user2 = UserBean.builder()
                .username("test2")
                .password("test2")
                .role(UserRoleEnum.MEMBER)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<UserBean> userList = userRepository.findAll();

        Assertions.assertNotNull(userList);
        Assertions.assertTrue(userList.size() >= 2);
    }

    @Test
    public void UserRepository_FindByUsername_ReturnUser(){
        UserBean user1 = UserBean.builder()
                .username("test1")
                .password("test1")
                .role(UserRoleEnum.ADMIN)
                .build();

        userRepository.save(user1);

        Optional<UserBean> savedUser = userRepository.findByUsername("test1");

        Assertions.assertNotNull(savedUser);
    }
}