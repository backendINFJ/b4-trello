package com.nbcamp.b4trello.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ColumnRequestDto {
    private Long boardId;
    private List<Long> columnIds;
    private String columnTitle;
}