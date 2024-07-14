package com.nbcamp.b4trello.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nbcamp.b4trello.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
