package com.nbcamp.b4trello.entity;

import com.nbcamp.b4trello.dto.BoardRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board { // 보드 수정 기능에서 필수예외처리 추가에 보드 이름,설명 데이터가 없는경우 예외처리 구현하라고 되어있는데 저는 entity클래스 에서
    // @NotBlank로 막아놔서 null일 경우가 사실상 없어서 기능 구현을 하지 않았습니다!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // 필수 데이터
    @NotBlank(message = "보드 이름은 필수 입력 값입니다.")
    private String boardName;

    @Column(nullable = false) // 필수 데이터
    @NotBlank(message = "보드 설명은 필수 입력 값입니다.")
    private String description;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "board")
    private List<UserBoard> userBoards;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "board")
    private List<Columns> columns;

    @Builder
    public Board(String boardName, String description) {
        this.boardName = boardName;
        this.description = description;
    }

    public void update(BoardRequestDto requestDTO) {
        this.boardName = requestDTO.getBoardName();
        this.description = requestDTO.getDescription();
    }
}
