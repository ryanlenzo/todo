package com.test.todo.service;

import com.test.todo.dao.TodoDao;
import com.test.todo.dao.TodoRepository;
import com.test.todo.dto.TodoDTO;
import com.test.todo.enum_.TodoStatusEnum;
import com.test.todo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final TodoDao todoDao;

    public TodoServiceImpl(TodoRepository todoRepository, TodoDao todoDao) {
        this.todoRepository = todoRepository;
        this.todoDao = todoDao;
    }

    @Override
    public List<Todo> findTodo(TodoStatusEnum status, String name, String sorting){
        TodoDTO todoQuery = new TodoDTO();
        todoQuery.setTodoStatus(status);
        todoQuery.setTodoName(name);
        todoQuery.setSorting(sorting);
        return todoDao.findListByCriteria(todoQuery);
    }

    @Override
    public Todo createTodo(TodoDTO dto) {
        Todo model = convertDTOToModel(dto);
        model.setStatus(TodoStatusEnum.NOT_STARTED);
        return todoRepository.save(model);
    }

    @Override
    public List<Todo> createBatchTodo(List<TodoDTO> dtoList) {
        List<Todo> modelList = new ArrayList<Todo>();
        for(TodoDTO dto : dtoList){
            Todo model = convertDTOToModel(dto);
            model.setStatus(TodoStatusEnum.NOT_STARTED);
            modelList.add(model);
        }
        return todoRepository.saveAll(modelList);
    }

    @Override
    public Todo updateTodo(TodoDTO dto) {
        Todo existingTodo = todoRepository.getById(dto.getTodoId());
        existingTodo.setName(dto.getTodoName());
        existingTodo.setDescription(dto.getTodoDescription());
        existingTodo.setDueDate(dto.getTodoDueDate());
        return todoRepository.save(existingTodo);
    }

    @Override
    public Todo updateTodoStatus(TodoDTO dto) {
        Todo existingTodo = todoRepository.getById(dto.getTodoId());
        existingTodo.setStatus(dto.getTodoStatus());
        return todoRepository.save(existingTodo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    private Todo convertDTOToModel(TodoDTO dto){
        Todo model = new Todo();
        model.setId(dto.getTodoId());
        model.setName(dto.getTodoName());
        model.setDescription(dto.getTodoDescription());
        model.setDueDate(dto.getTodoDueDate());
        model.setStatus(dto.getTodoStatus());
        return model;
    }
}
