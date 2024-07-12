package com.nbcamp.b4trello.repository;

import com.nbcamp.b4trello.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}