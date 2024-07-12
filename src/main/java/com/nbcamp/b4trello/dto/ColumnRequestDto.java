package com.nbcamp.b4trello.dto;

public class ColumnRequestDto {
        private Long boardId;
        private Long columnId;
        private String columnTitle;

        public Long getBoardId() {
            return boardId;
        }

        public Long getColumnId() {
            return columnId;
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
}