package com.test.todo.dao;

import com.test.todo.enum_.TodoStatusEnum;
import com.test.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByStatus(TodoStatusEnum statusEnum);
    List<Todo> findByName(String name);
    List<Todo> findByStatusAndName(TodoStatusEnum status, String name);
}
