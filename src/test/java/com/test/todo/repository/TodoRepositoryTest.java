package com.test.todo.repository;

import com.test.todo.dao.TodoRepository;
import com.test.todo.entity.TodoBean;
import com.test.todo.enums.TodoPriorityEnum;
import com.test.todo.enums.TodoStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void TodoRepository_Save_ReturnBook(){
        TodoBean todo = TodoBean.builder()
                .name("task 1")
                .dueDate(LocalDateTime.now())
                .status(TodoStatusEnum.NOT_STARTED)
                .priority(TodoPriorityEnum.CRITICAL)
                .build();

        TodoBean savedTodo = todoRepository.save(todo);

        Assertions.assertNotNull(savedTodo);
        Assertions.assertTrue(savedTodo.getId() > 0);
    }

    @Test
    public void TodoRepository_FindAll_ReturnMoreThanOneTodo(){
        TodoBean todo1 = TodoBean.builder()
                .name("task 1")
                .dueDate(LocalDateTime.now())
                .status(TodoStatusEnum.NOT_STARTED)
                .priority(TodoPriorityEnum.CRITICAL)
                .build();
        TodoBean todo2 = TodoBean.builder()
                .name("task 2")
                .dueDate(LocalDateTime.now())
                .status(TodoStatusEnum.NOT_STARTED)
                .priority(TodoPriorityEnum.MEDIUM)
                .build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);

        List<TodoBean> list = todoRepository.findAll();

        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.size() >= 2);
    }

    @Test
    public void TodoRepository_FindById_ReturnTodo(){
        TodoBean todo1 = TodoBean.builder()
                .name("task 1")
                .dueDate(LocalDateTime.now())
                .status(TodoStatusEnum.NOT_STARTED)
                .priority(TodoPriorityEnum.CRITICAL)
                .build();

        TodoBean savedTodo = todoRepository.save(todo1);

        Optional<TodoBean> todoOptional = todoRepository.findById(savedTodo.getId());

        Assertions.assertNotNull(todoOptional);
    }

    @Test
    public void TodoRepository_UpdateTodo_ReturnTodoNotNull(){
        TodoBean todo1 = TodoBean.builder()
                .name("task 1")
                .dueDate(LocalDateTime.now())
                .status(TodoStatusEnum.NOT_STARTED)
                .priority(TodoPriorityEnum.CRITICAL)
                .build();

        TodoBean savedTodo = todoRepository.save(todo1);
        Optional<TodoBean> userOptional = todoRepository.findById(savedTodo.getId());
        TodoBean todo = userOptional.get();
        todo.setName("task 2");
        TodoBean updatedTodo = todoRepository.save(todo);
        Assertions.assertNotNull(updatedTodo.getName());
    }

    @Test
    public void TodoRepository_DeleteTodo_ReturnTodoIsEmpty(){
        TodoBean todo1 = TodoBean.builder()
                .name("task 1")
                .dueDate(LocalDateTime.now())
                .status(TodoStatusEnum.NOT_STARTED)
                .priority(TodoPriorityEnum.CRITICAL)
                .build();

        TodoBean savedTodo = todoRepository.save(todo1);
        todoRepository.deleteById(savedTodo.getId());
        Optional<TodoBean> todoOptional = todoRepository.findById(savedTodo.getId());

        Assertions.assertFalse(todoOptional.isPresent());
    }
}
