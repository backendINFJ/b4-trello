package com.nbcamp.b4trello.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    @Column(columnDefinition = "varchar(30) default 'USER'", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Builder
    public UserBoard(User user, Board board, UserType userType) {
        this.user = user;
        this.board = board;
        this.userType = userType;
    }


}

