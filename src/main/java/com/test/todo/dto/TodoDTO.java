package com.test.todo.dto;

import com.test.todo.enums.TodoPriorityEnum;
import com.test.todo.enums.TodoStatusEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class TodoDTO {
    private Long id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;
    private TodoStatusEnum status;
    private TodoPriorityEnum priority;
    private String tag;

    private Boolean deleted;
    //for filtering and sorting
    private String sorting;
}
