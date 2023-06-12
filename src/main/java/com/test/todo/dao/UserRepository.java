package com.test.todo.dao;

import com.test.todo.entity.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserBean, Long> {

    Optional<UserBean> findByUsername(String username);
}
