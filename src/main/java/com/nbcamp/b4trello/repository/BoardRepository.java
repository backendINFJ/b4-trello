package com.nbcamp.b4trello.repository;

import com.nbcamp.b4trello.entity.Board;
import com.nbcamp.b4trello.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findAllByUser(User user);

    Board findByBoardId(Long boardId);
}
