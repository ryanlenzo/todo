package com.test.todo.dao;

import com.test.todo.entity.TodoBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoBean, Long> {
}
