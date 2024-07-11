package com.nbcamp.b4trello.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "column")
public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String columnTitle;

    private LocalDateTime createdAt;

    private Integer columnSequence;

    public Long getColumnId() {
        return columnId;
    }

    public Board getBoard() {
        return board;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getColumnSequence() {
        return columnSequence;
    }
}
