package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

	//user error

	//board error

	//column error
	COLUMN_NOT_FOUND("컬럼을 찾을 수 없습니다."),
	COLUMN_ALREADY_EXISTS("이미 존재하는 컬럼 제목입니다.");

	//card error
//	CARD_NOT_FOUND("카드를 찾을 수 없습니다.");

	private final String message;

}
