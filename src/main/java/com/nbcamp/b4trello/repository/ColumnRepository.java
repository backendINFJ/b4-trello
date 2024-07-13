package com.nbcamp.b4trello.repository;

import com.nbcamp.b4trello.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ColumnRepository extends JpaRepository<Columns, Long> {
    List<Columns> findByBoardIdOrderByColumnSequenceAsc(Long boardId);
    boolean existsByBoardIdAndColumnTitle(Long boardId, String columnTitle);
}