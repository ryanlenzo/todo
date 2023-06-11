package com.test.todo.dao;

import com.test.todo.dto.TodoDTO;
import com.test.todo.model.Todo;

import java.util.List;

public interface TodoDao {
    List<Todo> findListByCriteria(TodoDTO todo);
}
