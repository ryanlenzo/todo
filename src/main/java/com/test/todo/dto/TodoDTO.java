package com.test.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.todo.enum_.TodoStatusEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TodoDTO {
    private Long todoId;
    private String todoName;
    private String todoDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime todoDueDate;
    private TodoStatusEnum todoStatus;
    private String sorting;
}
