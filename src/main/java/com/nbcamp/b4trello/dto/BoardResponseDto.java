package com.nbcamp.b4trello.dto;

import com.nbcamp.b4trello.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private String boardName;

    private String description;

    public BoardResponseDto(Board board) {
        this.boardName = board.getBoardName();
        this.description = board.getDescription();
    }
}
