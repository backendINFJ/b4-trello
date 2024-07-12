package com.nbcamp.b4trello.dto;

import java.util.Collections;
import java.util.List;

public class ColumnRequestDto {
        private Long boardId;
        private Long columnId;
        private String columnTitle;

        public Long getBoardId() {
            return boardId;
        }

        public void setBoardId(Long boardId) {
            this.boardId = boardId;
        }

        public List<Long> getColumnId() {
            return Collections.singletonList(columnId);
        }

        public void setColumnIds(Long columnId) {
            this.columnId = columnId;
        }

        public String getColumnTitle() {
            return columnTitle;
        }

        public void setColumnTitle(String columnTitle) {
            this.columnTitle = columnTitle;
        }
}