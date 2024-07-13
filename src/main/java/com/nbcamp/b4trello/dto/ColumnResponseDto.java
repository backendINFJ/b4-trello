package com.nbcamp.b4trello.dto;

import com.nbcamp.b4trello.entity.Columns;

public class ColumnResponseDto {
    private Long columnId;
    private Long boardId;
    private String columnTitle;
    private Integer columnSequence;

    public ColumnResponseDto(Columns columns) {
        this.columnId = columns.getColumnId();
        this.boardId = columns.getBoard().getId();
        this.columnTitle = columns.getColumnTitle();
        this.columnSequence = columns.getColumnSequence();
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