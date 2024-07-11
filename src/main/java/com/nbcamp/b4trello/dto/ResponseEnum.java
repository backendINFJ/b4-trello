package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

	CREATE_CARD("카드 생성 완료", HttpStatus.CREATED),
	CREATE_BOARD("보드 생성 완료", HttpStatus.CREATED);

	private final String message;
	private final HttpStatus httpStatus;
}
