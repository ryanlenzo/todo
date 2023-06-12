package com.test.todo.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultMessage<T> {
    //USER Result Message
    public static final String MESSAGE_USER_EXIST = "Username already exists";
    public static final String MESSAGE_USER_REGISTER = "Registration successfully";
    public static final String MESSAGE_USER_LOGIN_SUCCESS = "Login successfully";
    public static final String MESSAGE_USER_LOGIN_FAILED = "Invalid username or password";
    //TODO Result Message
    public static final String MESSAGE_TODO_FIND = "TODO list found";
    public static final String MESSAGE_TODO_CREATE = "TODO created";
    public static final String MESSAGE_TODO_CREATE_BATCH = "TODO batch created";
    public static final String MESSAGE_TODO_UPDATE = "TODO updated";
    public static final String MESSAGE_TODO_UPDATE_STATUS = "TODO status updated";
    public static final String MESSAGE_TODO_ID_NOT_FOUND = "TODO ID not found";
    public static final String MESSAGE_TODO_DELETE = "TODO deleted";
    public static final String MESSAGE_TODO_ACTIVITY_FIND = "TODO Activity List found";
    private boolean success;
    private String message;
    private T data;

    public ResultMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
