package com.nbcamp.b4trello.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class BoardRequestDto {

    private String boardName;

    private String description;


    public BoardRequestDto(String boardName, String description) {
        this.boardName = boardName;
        this.description = description;
    }
}
