package com.nbcamp.b4trello.service;

import com.nbcamp.b4trello.entity.Column;
import com.nbcamp.b4trello.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ColumnService {

    @Autowired
    private ColumnRepository columnRepository;

    public List<Column> getColumns(Long boardId) {
        return columnRepository.findByBoardIdOrderByColumnSequenceAsc(boardId);
    }
}