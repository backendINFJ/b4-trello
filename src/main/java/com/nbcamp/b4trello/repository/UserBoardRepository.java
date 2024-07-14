package com.nbcamp.b4trello.repository;

import java.util.List;
import java.util.Optional;

import com.nbcamp.b4trello.entity.Board;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
	Optional<UserBoard> findByUserEmail(String userEmail);

	Optional<UserBoard> findByUserAndBoardId(User user, Long boardId);

	Optional<UserBoard> findByUserAndBoard(User user, Board board);
}
