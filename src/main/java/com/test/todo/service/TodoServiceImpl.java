package com.test.todo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.todo.dao.TodoActivityRepository;
import com.test.todo.dao.UserRepository;
import com.test.todo.entity.TodoActivityBean;
import com.test.todo.entity.UserBean;
import com.test.todo.enums.TodoStatusEnum;
import com.test.todo.util.ResultMessage;
import com.test.todo.dao.TodoDao;
import com.test.todo.dao.TodoRepository;
import com.test.todo.dto.TodoDTO;
import com.test.todo.entity.TodoBean;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.test.todo.util.ResultMessage.*;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoDao todoDao;
    private final UserRepository userRepository;
    private final TodoActivityRepository todoActivityRepository;

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public TodoServiceImpl(TodoRepository todoRepository, TodoDao todoDao, UserRepository userRepository, TodoActivityRepository todoActivityRepository) {
        this.todoRepository = todoRepository;
        this.todoDao = todoDao;
        this.userRepository = userRepository;
        this.todoActivityRepository = todoActivityRepository;
    }

    @Override
    public ResultMessage<?> findTodo(TodoDTO dto) {
        return new ResultMessage<>(true, ResultMessage.MESSAGE_TODO_FIND, convertModelToDTO(todoDao.findListByCriteria(dto)));
    }

    @Override
    public ResultMessage<?> createTodo(TodoDTO dto) {
        TodoBean model = convertDTOToModel(dto);
        model.setStatus(TodoStatusEnum.NOT_STARTED);
        TodoBean savedTodo = todoRepository.save(model);
        saveTodoActivity("CREATE", savedTodo.getId(), dto);
        return new ResultMessage<>(true, ResultMessage.MESSAGE_TODO_CREATE);
    }

    @Override
    public ResultMessage<?> createBatchTodo(List<TodoDTO> dtoList) {
        List<TodoBean> modelList = new ArrayList<TodoBean>();
        for (TodoDTO dto : dtoList) {
            TodoBean model = convertDTOToModel(dto);
            model.setStatus(TodoStatusEnum.NOT_STARTED);
            modelList.add(model);
        }
        List<TodoBean> todoList = todoRepository.saveAll(modelList);
        for (int i = 0; i < todoList.size(); i++) {
            saveTodoActivity("CREATE", todoList.get(i).getId(), dtoList.get(i));
        }
        return new ResultMessage<>(true, MESSAGE_TODO_CREATE_BATCH);
    }

    @Override
    public ResultMessage<?> updateTodo(TodoDTO dto) {
        Optional<TodoBean> existingTodoBean = todoRepository.findById(dto.getId());
        if(existingTodoBean.isPresent()){
            TodoBean todo = existingTodoBean.get();
            todo.setName(dto.getName());
            todo.setDescription(dto.getDescription());
            todo.setDueDate(dto.getDueDate());
            todo.setPriority(dto.getPriority());
            todo.setTag(dto.getTag());
            TodoBean updatedTodo = todoRepository.save(todo);
            saveTodoActivity("UPDATE", updatedTodo.getId(), dto);
            return new ResultMessage<>(true, MESSAGE_TODO_UPDATE);
        }else{
            return new ResultMessage<>(false, MESSAGE_TODO_ID_NOT_FOUND);
        }
    }

    @Override
    public ResultMessage<?> updateTodoStatus(TodoDTO dto) {
        Optional<TodoBean> existingTodoBean = todoRepository.findById(dto.getId());
        if(existingTodoBean.isPresent()){
            TodoBean todo = existingTodoBean.get();
            todo.setStatus(dto.getStatus());
            TodoBean updatedTodo = todoRepository.save(todo);
            dto.setName(todo.getName());
            saveTodoActivity("UPDATE_STATUS", updatedTodo.getId(), dto);
            return new ResultMessage<>(true, MESSAGE_TODO_UPDATE_STATUS);
        }else{
            return new ResultMessage<>(false, MESSAGE_TODO_ID_NOT_FOUND);
        }
    }

    @Override
    public ResultMessage<?> deleteTodo(Long id) {
        Optional<TodoBean> existingTodoBean = todoRepository.findById(id);
        if(existingTodoBean.isPresent()){
            TodoBean todo = existingTodoBean.get();
            todo.setDeleted(true);
            TodoBean updatedTodo = todoRepository.save(todo);
            saveTodoActivity("DELETE", updatedTodo.getId(), TodoDTO.builder().id(id).deleted(true).build());
            return new ResultMessage<>(true, MESSAGE_TODO_DELETE);
        }else{
            return new ResultMessage<>(false, MESSAGE_TODO_ID_NOT_FOUND);
        }
    }

    private TodoBean convertDTOToModel(TodoDTO dto) {
        return modelMapper.map(dto, TodoBean.class);
    }

    private List<TodoDTO> convertModelToDTO(List<TodoBean> todoList) {
        List<TodoDTO> result = new ArrayList<TodoDTO>();
        for (TodoBean todo : todoList) {
            result.add(TodoDTO
                    .builder()
                    .id(todo.getId())
                    .name(todo.getName())
                    .description(todo.getDescription())
                    .status(todo.getStatus())
                    .priority(todo.getPriority())
                    .tag(todo.getTag())
                    .build());
        }
        return result;
    }

    private void saveTodoActivity(String action, Long todoId, TodoDTO dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserBean existingUser = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        String activityFeed = null;

        switch (action) {
            case "CREATE":
                activityFeed = String.format("%s has created a TODO named %s", userDetails.getUsername(), dto.getName());
                break;
            case "UPDATE":
                activityFeed = String.format("%s has updated the description of TODO named %s", userDetails.getUsername(), dto.getName());
                break;
            case "UPDATE_STATUS":
                activityFeed = String.format("%s has updated the status of TODO named %s to %s", userDetails.getUsername(), dto.getName(), dto.getStatus().name());
                break;
            case "DELETE":
                activityFeed = String.format("%s has deleted the TODO named %s", userDetails.getUsername(), dto.getName());
                break;
        }

        try {
            TodoActivityBean todoActivity = TodoActivityBean.builder()
                    .todo(new TodoBean(todoId))
                    .user(existingUser)
                    .action(action)
                    .jsonData(objectMapper.writeValueAsString(dto))
                    .activityFeed(activityFeed)
                    .createdDatetime(LocalDateTime.now())
                    .build();
            todoActivityRepository.save(todoActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
