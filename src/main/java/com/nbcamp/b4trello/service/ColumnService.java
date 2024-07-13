package com.nbcamp.b4trello.service;

import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.entity.Board;
import com.nbcamp.b4trello.entity.Column;
import com.nbcamp.b4trello.repository.BoardRepository;
import com.nbcamp.b4trello.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
        checkAuthorization();
        if (!boardRepository.existsById(boardId)) {
            throw new IllegalArgumentException(ErrorMessageEnum.BOARD_NOT_FOUND.getMessage());
        }
        return columnRepository.findByBoardIdOrderByColumnSequenceAsc(boardId);
    }

    public Column createColumn(Long boardId, String columnTitle) {
        checkAuthorization();
        if (!boardRepository.existsById(boardId)) {
            throw new IllegalArgumentException(ErrorMessageEnum.BOARD_NOT_FOUND.getMessage());
        }
        if (columnRepository.existsByBoardIdAndColumnTitle(boardId, columnTitle)) {
            throw new IllegalArgumentException(ErrorMessageEnum.COLUMN_TITLE_ALREADY_EXISTS.getMessage());
        }
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.BOARD_NOT_FOUND.getMessage()));
        int columnSequence = columnRepository.findByBoardIdOrderByColumnSequenceAsc(boardId).size() + 1;
        Column column = new Column();
        column.setBoard(board);
        column.setColumnTitle(columnTitle);
        column.setCreatedAt(LocalDateTime.now());
        column.setColumnSequence(columnSequence);
        return columnRepository.save(column);
    }

    public void deleteColumn(Long columnId) {
        checkAuthorization();
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage()));
        columnRepository.delete(column);
    }

    public void updateColumnSequence(Long boardId, List<Long> columnIds) {
        checkAuthorization();
        if (!boardRepository.existsById(boardId)) {
            throw new IllegalArgumentException(ErrorMessageEnum.BOARD_NOT_FOUND.getMessage());
        }
        List<Column> columns = columnRepository.findByBoardIdOrderByColumnSequenceAsc(boardId);
        if (columns.size() != columnIds.size()) {
            throw new IllegalArgumentException(ErrorMessageEnum.COLUMN_SEQUENCE_MISMATCH.getMessage());
        }
        for (int i = 0; i < columnIds.size(); i++) {
            final Long currentColumnId = columnIds.get(i);
            Column column = columns.stream()
                    .filter(c -> c.getColumnId().equals(currentColumnId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage()));
            column.setColumnSequence(i + 1);
            columnRepository.save(column);
        }
    }

    private void checkAuthorization() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGER"))) {
            throw new AccessDeniedException("접근이 거부되었습니다");
        }
    }
}