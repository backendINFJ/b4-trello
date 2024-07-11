package com.nbcamp.b4trello.dto;

import com.nbcamp.b4trello.entity.Column;

public class ColumnResponseDto {
    private Long columnId;
    private Long boardId;
    private String columnTitle;
    private Integer columnSequence;

    public ColumnResponseDto(Column column) {
        this.columnId = column.getColumnId();
        this.boardId = column.getBoard().getBoardId();
        this.columnTitle = column.getColumnTitle();
        this.columnSequence = column.getColumnSequence();
    }
}