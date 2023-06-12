package com.test.todo.entity;

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
@Table(name = "todo_activity")
public class TodoActivityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private TodoBean todo;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserBean user;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jsonData;

    @Column(nullable = false)
    private String activityFeed;

    @Column(nullable = false)
    private LocalDateTime createdDatetime;
}