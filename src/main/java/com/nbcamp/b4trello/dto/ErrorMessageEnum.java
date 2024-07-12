package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

	// User errors
	USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
	USER_NOT_AUTHORIZED("사용자에게 권한이 없습니다."),

	// Board errors
	BOARD_NOT_FOUND("보드를 찾을 수 없습니다."),
	BOARD_ACCESS_DENIED("보드 접근이 거부되었습니다."),

	// Column errors
	COLUMN_NOT_FOUND("컬럼을 찾을 수 없습니다."),
	COLUMN_TITLE_ALREADY_EXISTS("컬럼 제목이 이미 존재합니다."),
	COLUMN_SEQUENCE_MISMATCH("컬럼 순서가 일치하지 않습니다."),
	COLUMN_NOT_SEQUENCE("컬럼 순서를 찾을 수 없습니다."),

	// Card errors
	CARD_NOT_FOUND("카드를 찾을 수 없습니다.");

	private final String message;
}