package com.nbcamp.b4trello.repository;

import com.nbcamp.b4trello.entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ColumnRepository extends JpaRepository<Column, Long> {
    List<Column> findByBoardIdOrderByColumnSequenceAsc(Long boardId);
}