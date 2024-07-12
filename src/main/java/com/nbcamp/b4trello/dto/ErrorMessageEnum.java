package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

	//user error
	USER_NOT_FOUND("유저를 찾을수 없습니다."),
	USER_DENIND("탈퇴한 유저입니다."),
	//board error

	//column error

	//card error
	CARD_NOT_FOUND("카드를 찾을 수 없습니다.");

	private final String message;

}
