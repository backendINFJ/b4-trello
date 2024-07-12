package com.nbcamp.b4trello.dto;

public class ColumnRequestDto {
        private Long boardId;
        private Long columnIds;

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
}