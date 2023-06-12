package com.test.todo.service;

import com.test.todo.util.ResultMessage;
import com.test.todo.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    ResultMessage<?> findTodo(TodoDTO todo);
    ResultMessage<?> createTodo(TodoDTO todo);
    ResultMessage<?> createBatchTodo(List<TodoDTO> todo);
    ResultMessage<?> updateTodo(TodoDTO todo);
    ResultMessage<?> updateTodoStatus(TodoDTO todo);
    ResultMessage<?> deleteTodo(Long id);
}
