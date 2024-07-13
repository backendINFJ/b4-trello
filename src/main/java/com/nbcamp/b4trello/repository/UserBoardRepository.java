package com.nbcamp.b4trello.repository;

import com.nbcamp.b4trello.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
}
