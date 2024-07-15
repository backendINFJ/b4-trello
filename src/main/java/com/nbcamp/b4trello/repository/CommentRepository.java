package com.nbcamp.b4trello.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nbcamp.b4trello.entity.Card;
import com.nbcamp.b4trello.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Collection<Comment> findAllByCard(Card card);
}