package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

	CREATE_CARD("카드 생성 완료", HttpStatus.CREATED),
	GET_CARD("카드 조회 완료", HttpStatus.OK),
	UPDATE_CARD("카드 수정 완료", HttpStatus.OK),;

	private final String message;
	private final HttpStatus httpStatus;
}
