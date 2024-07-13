package com.nbcamp.b4trello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nbcamp.b4trello.entity.Board;
import com.nbcamp.b4trello.entity.User;

public interface BoardRepository extends JpaRepository<Board, Long> {

	List<Board> findAllByUser(User user);

	Board findByBoardId(Long boardId);
}
