package com.test.todo.dao;

import com.test.todo.dto.TodoDTO;
import com.test.todo.entity.TodoBean;

import java.util.List;

public interface TodoDao {
    List<TodoBean> findListByCriteria(TodoDTO todo);
}
