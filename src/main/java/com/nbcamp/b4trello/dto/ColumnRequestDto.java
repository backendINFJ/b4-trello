package com.nbcamp.b4trello.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ColumnRequestDto {
    private Long boardId;
    private Long columnId;
    private String columnTitle;

    public List<Long> getColumnIds() {
        return Collections.singletonList(columnId);
    }
}