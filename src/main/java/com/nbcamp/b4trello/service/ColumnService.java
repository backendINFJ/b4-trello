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
import java.util.Optional;

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
        Column column = columnRepository.findById(columnId).orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage()));
        columnRepository.delete(column);
    }

    public void updateColumnSequence(Long boardId, List<Long> columnIds) {
        List<Column> columns = columnRepository.findByBoardIdOrderByColumnSequenceAsc(boardId);
        for (int i = 0; i < columnIds.size(); i++) {
            final Long columnId = columnIds.get(i);
            Column column = columns.stream()
                    .filter(c -> c.getColumnId().equals(columnId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_SEQUENCE.getMessage()));
            column.setColumnSequence(i + 1);
            columnRepository.save(column);
        }
    }
}