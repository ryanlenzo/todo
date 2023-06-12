package com.test.todo.entity;

import com.test.todo.enums.TodoPriorityEnum;
import com.test.todo.enums.TodoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "todo")
public class TodoBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;
    @Column(columnDefinition = "VARCHAR(255)")
    private String description;
    @Column(nullable = false)
    private LocalDateTime dueDate;
    @Column(nullable = false, columnDefinition = "TINYINT(1) default 0")
    private TodoStatusEnum status;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TodoPriorityEnum priority;
    private String tag;
    @Column(columnDefinition = "bit default 0")
    private boolean deleted;
    public TodoBean(Long todoId) {
        this.id = todoId;
    }
}
