package com.test.todo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TodoActivityDTO {
    Long id;
    String activityFeedMessage;
    LocalDateTime activityTime;
}
