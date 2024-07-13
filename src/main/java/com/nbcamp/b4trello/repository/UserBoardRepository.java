package com.nbcamp.b4trello.repository;

import java.util.List;
import java.util.Optional;

import com.nbcamp.b4trello.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
	Optional<UserBoard> findByUserEmail(String userEmail);

	List<UserBoard> findByUserId(Long userId);
}
