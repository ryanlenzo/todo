package com.test.todo.controller;

import com.test.todo.common.ResultMessage;
import com.test.todo.dto.TodoDTO;
import com.test.todo.enum_.TodoStatusEnum;
import com.test.todo.model.Todo;
import com.test.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/list")
    public ResultMessage<?> findTodo(@RequestParam(required = false) TodoStatusEnum status, @RequestParam(required = false) String name, @RequestParam(required = false) String sorting) {
        List<Todo> list = todoService.findTodo(status, name, sorting);
        return list != null ?
            new ResultMessage<>(true, "TODO list found.", list) :
            new ResultMessage<>(false, "Unable to find TODO list.");
    }

    @PostMapping("/create")
    public ResultMessage<String> createTodo(@RequestBody TodoDTO todo) {
        return todoService.createTodo(todo) != null ?
                new ResultMessage<>(true, "TODO created successfully.") :
                new ResultMessage<>(false, "Unable create the TODO.");
    }

    @PostMapping("/createBatch")
    public ResultMessage<String> createBatchTodo(@RequestBody List<TodoDTO> todo) {
        return todoService.createBatchTodo(todo) != null ?
                new ResultMessage<>(true, "TODO created successfully.") :
                new ResultMessage<>(false, "Unable create the TODO.");
    }

    @PatchMapping("/update/{id}")
    public ResultMessage<String> updateTodo(@PathVariable Long id, @RequestBody TodoDTO todo) {
        todo.setTodoId(id);
        return todoService.updateTodo(todo) != null ?
                new ResultMessage<>(true, "TODO updated successfully.") :
                new ResultMessage<>(false, "Unable updated the TODO.");
    }

    @PutMapping("/updateStatus/{id}")
    public ResultMessage<String> updateTodoStatus(@PathVariable Long id, @RequestBody TodoDTO todo) {
        todo.setTodoId(id);
        return todoService.updateTodoStatus(todo) != null ?
                new ResultMessage<>(true, "TODO updated successfully.") :
                new ResultMessage<>(false, "Unable updated the TODO.");
    }

    @DeleteMapping("/delete/{id}")
    public ResultMessage<String> deleteEntity(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return new ResultMessage<>(true, "TODO deleted successfully.");
    }
}
