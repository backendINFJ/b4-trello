package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

	CREATE_CARD("카드 생성 완료", HttpStatus.CREATED),
	CREATE_BOARD("보드 생성 완료", HttpStatus.CREATED),
	READ_BOARD("보드 전체조회 완료",HttpStatus.OK),
	UPDATE_BOARD("보드 수정 완료",HttpStatus.OK),
	DELETE_BOARD("보드 삭제 완료",HttpStatus.OK);

	private final String message;
	private final HttpStatus httpStatus;
}
