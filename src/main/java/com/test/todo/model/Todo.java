package com.test.todo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.todo.enum_.TodoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;
    @Column(columnDefinition = "VARCHAR(255)")
    private String description;
    @Column(nullable = false)
    private LocalDateTime dueDate;
    @Column(nullable = false)
    private TodoStatusEnum status;
}
