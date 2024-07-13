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

    // Getters and Setters
    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getColumnSequence() {
        return columnSequence;
    }

    public void setColumnSequence(Integer columnSequence) {
        this.columnSequence = columnSequence;
    }
}