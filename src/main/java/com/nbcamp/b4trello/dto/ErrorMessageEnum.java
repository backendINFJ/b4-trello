package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

	//user error

	//board error

	//column error

	//card error
	CARD_NOT_FOUND("카드를 찾을 수 없습니다."),
	MISMATCH_USER("작성자만 가능합니다.");

	private final String message;

}
