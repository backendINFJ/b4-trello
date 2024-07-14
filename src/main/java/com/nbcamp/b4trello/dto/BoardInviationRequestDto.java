package com.nbcamp.b4trello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BoardInviationRequestDto {

    private Long boardId;

    private String userEmail;
}
