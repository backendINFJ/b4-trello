package com.nbcamp.b4trello.service;

import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.entity.Board;
import com.nbcamp.b4trello.entity.Column;
import com.nbcamp.b4trello.repository.BoardRepository;
import com.nbcamp.b4trello.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ColumnService {

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private BoardRepository boardRepository;

    public List<Column> getColumns(Long boardId) {
        return columnRepository.findByBoardIdOrderByColumnSequenceAsc(boardId);
    }

    public Column createColumn(Long boardId, String columnTitle) {
        if (!boardRepository.existsById(boardId)) {
            throw new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage());
        }
        if (columnRepository.existsByBoardIdAndColumnTitle(boardId, columnTitle)) {
            throw new IllegalArgumentException(ErrorMessageEnum.COLUMN_ALREADY_EXISTS.getMessage());
        }
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage()));
        int columnSequence = columnRepository.findByBoardIdOrderByColumnSequenceAsc(boardId).size() + 1;
        Column column = new Column();
        column.setBoard(board);
        column.setColumnTitle(columnTitle);
        column.setCreatedAt(LocalDateTime.now());
        column.setColumnSequence(columnSequence);
        return columnRepository.save(column);
    }

    public void deleteColumn(Long columnId) {
        Column column = columnRepository.findById(columnId).orElseThrow(() -> new IllegalArgumentException("Column not found"));
        columnRepository.delete(column);
    }
}