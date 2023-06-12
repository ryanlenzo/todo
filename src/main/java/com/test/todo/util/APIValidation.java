package com.test.todo.util;

import com.test.todo.dto.AuthenticationDTO;
import com.test.todo.dto.TodoDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class APIValidation {
    //AUTH CONTROLLER
    public static ResultMessage<?> validateAuthRegister(AuthenticationDTO dto) {
        if(StringUtils.isBlank(dto.getUsername())) return new ResultMessage<>(false,"Username is required");
        if(StringUtils.isBlank(dto.getPassword())) return new ResultMessage<>(false,"Password is required");
        if(StringUtils.isBlank(dto.getRole())) return new ResultMessage<>(false,"Role is required");
        return null;
    }

    public static ResultMessage<?> validateAuthLogin(AuthenticationDTO dto) {
        if(StringUtils.isBlank(dto.getUsername())) return new ResultMessage<>(false,"Username is required");
        if(StringUtils.isBlank(dto.getPassword())) return new ResultMessage<>(false,"Password is required");
        return null;
    }
    //TODO CONTROLLER
    public static ResultMessage<?> validateTodoCreate(TodoDTO dto) {
        if(StringUtils.isBlank(dto.getName())) return new ResultMessage<>(false,"Name is required");
        if(dto.getDueDate() == null) return new ResultMessage<>(false,"Due Date is required");
        if(dto.getPriority() == null) return new ResultMessage<>(false,"Priority is required");
        return null;
    }

    public static ResultMessage<?> validateTodoCreateBatch(List<TodoDTO> dtoList) {
        for(TodoDTO dto : dtoList){
            if(StringUtils.isBlank(dto.getName())) return new ResultMessage<>(false,"Name is required");
            if(dto.getDueDate() == null) return new ResultMessage<>(false,"Due Date is required");
            if(dto.getPriority() == null) return new ResultMessage<>(false,"Priority is required");
        }
        return null;
    }

    public static ResultMessage<?> validateTodoUpdate(TodoDTO dto) {
        if(dto.getId() == null) return new ResultMessage<>(false,"ID is required");
        if(StringUtils.isBlank(dto.getName())) return new ResultMessage<>(false,"Name is required");
        if(dto.getDueDate() == null) return new ResultMessage<>(false,"Due Date is required");
        if(dto.getPriority() == null) return new ResultMessage<>(false,"Priority is required");
        return null;
    }

    public static ResultMessage<?> validateTodoUpdateStatus(TodoDTO dto) {
        if(dto.getId() == null) return new ResultMessage<>(false,"ID is required");
        if(dto.getStatus() == null) return new ResultMessage<>(false,"Status is required");
        return null;
    }

    public static ResultMessage<?> validateTodoDelete(Long id) {
        if(id == null) return new ResultMessage<>(false,"ID is required");
        return null;
    }
}
