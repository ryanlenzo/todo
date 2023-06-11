package com.test.todo.service;

import com.test.todo.dto.TodoDTO;
import com.test.todo.enum_.TodoStatusEnum;
import com.test.todo.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> findTodo(TodoStatusEnum status, String name, String sorting);
    Todo createTodo(TodoDTO todo);
    List<Todo> createBatchTodo(List<TodoDTO> todo);
    Todo updateTodo(TodoDTO todo);

    Todo updateTodoStatus(TodoDTO todo);

    void deleteTodo(Long id);
}
