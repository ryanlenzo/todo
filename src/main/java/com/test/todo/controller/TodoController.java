package com.test.todo.controller;

import com.test.todo.service.TodoActivityService;
import com.test.todo.util.APIValidation;
import com.test.todo.util.ResultMessage;
import com.test.todo.dto.TodoDTO;
import com.test.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoActivityService todoActivityService;

    @PostMapping("/mb/list")
    public ResultMessage<?> findTodo(@RequestBody TodoDTO todo) {
        return todoService.findTodo(todo);
    }

    @PostMapping("/am/create")
    public ResultMessage<?> createTodo(@RequestBody TodoDTO dto) {
        ResultMessage<?> validationError = APIValidation.validateTodoCreate(dto);
        return (validationError != null) ? validationError : todoService.createTodo(dto);
    }

    @PostMapping("/am/createBatch")
    public ResultMessage<?> createBatchTodo(@RequestBody List<TodoDTO> dtoList) {
        ResultMessage<?> validationError = APIValidation.validateTodoCreateBatch(dtoList);
        return (validationError != null) ? validationError : todoService.createBatchTodo(dtoList);
    }

    @PatchMapping("/am/update/{id}")
    public ResultMessage<?> updateTodo(@PathVariable Long id, @RequestBody TodoDTO dto) {
        dto.setId(id);
        ResultMessage<?> validationError = APIValidation.validateTodoUpdate(dto);
        return (validationError != null) ? validationError : todoService.updateTodo(dto);
    }

    @PutMapping("/mb/updateStatus/{id}")
    public ResultMessage<?> updateTodoStatus(@PathVariable Long id, @RequestBody TodoDTO dto) {
        dto.setId(id);
        ResultMessage<?> validationError = APIValidation.validateTodoUpdateStatus(dto);
        return (validationError != null) ? validationError : todoService.updateTodoStatus(dto);
    }

    @DeleteMapping("/am/delete/{id}")
    public ResultMessage<?> deleteEntity(@PathVariable("id") Long id) {
        ResultMessage<?> validationError = APIValidation.validateTodoDelete(id);
        return (validationError != null) ? validationError : todoService.deleteTodo(id);
    }

    @GetMapping("/mb/activityList")
    public ResultMessage<?> findTodoActivity() {
        return todoActivityService.findTodoActivity();
    }
}
