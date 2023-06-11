package com.test.todo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
public class ResultMessage<T> {
    private boolean success;
    private String message;
    private T data;

    public ResultMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
