package com.nbcamp.b4trello.entity;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // 필수 데이터
    @NotBlank(message = "보드 이름은 필수 입력 값입니다.")
    private String boardName;

    @Column(nullable = false) // 필수 데이터
    @NotBlank(message = "보드 설명은 필수 입력 값입니다.")
    private String description;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<UserBoard> userBoards;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Column> columns;

    @Builder
    public Board(String boardName, String description) {
        this.boardName = boardName;
        this.description = description;
    }
}
