package com.nbcamp.b4trello.dto;

import com.nbcamp.b4trello.entity.Column;

public class ColumnResponseDto {
    private Long columnId;
    private Long boardId;
    private String columnTitle;
    private Integer columnSequence;

    public ColumnResponseDto(Column column) {
        this.columnId = column.getColumnId();
        this.boardId = column.getBoard().getId();
        this.columnTitle = column.getColumnTitle();
        this.columnSequence = column.getColumnSequence();
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public Integer getColumnSequence() {
        return columnSequence;
    }

    public void setColumnSequence(Integer columnSequence) {
        this.columnSequence = columnSequence;
    }
}