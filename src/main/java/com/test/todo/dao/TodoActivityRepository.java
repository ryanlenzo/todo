package com.test.todo.dao;

import com.test.todo.entity.TodoActivityBean;
import com.test.todo.entity.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoActivityRepository extends JpaRepository<TodoActivityBean, Long> {
    List<TodoActivityBean> findAllByOrderByCreatedDatetimeDesc();
}
