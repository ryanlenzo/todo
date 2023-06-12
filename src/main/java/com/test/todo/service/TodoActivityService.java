package com.test.todo.service;

import com.test.todo.entity.TodoActivityBean;
import com.test.todo.util.ResultMessage;

import java.util.List;

public interface TodoActivityService {
    ResultMessage<?> findTodoActivity();
}
