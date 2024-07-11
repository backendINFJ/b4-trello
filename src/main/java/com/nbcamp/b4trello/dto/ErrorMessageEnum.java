package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

    //user error
    USER_NOT_FOUND("유저를 찾을 수 없습니다."),

    //board error
    BOARD_NOT_FOUND("보드를 찾을 수 없습니다."),
    BOARD_NOT_DATA("보드 생성에 필요한 필수 데이터가 없습니다."),  // entity에서 예외처리 고민중 -> @NotBlank
    BOARD_NOT_UNAUTHORIZED("권한에 맞지않는 사용자 입니다."),


    //column error

    //card error
    CARD_NOT_FOUND("카드를 찾을 수 없습니다.");

    private final String message;

}
