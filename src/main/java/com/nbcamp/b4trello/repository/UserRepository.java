package com.nbcamp.b4trello.repository;

import com.nbcamp.b4trello.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
