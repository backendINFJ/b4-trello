package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

	SUCCESS(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
	CREATED(HttpStatus.CREATED, "리소스가 성공적으로 생성되었습니다."),
	NO_CONTENT(HttpStatus.NO_CONTENT, "리소스가 성공적으로 삭제되었습니다."),
	CREATE_USER("회원 가입 완료", HttpStatus.CREATED),
	UPDATE_USER("정보 변경 완료", HttpStatus.OK),
	USER_LOGOUT("로그아웃 완료", HttpStatus.OK),
	SEND_MAIL("메일전송 완료", HttpStatus.OK),
	ACCESS_MAIL("메일 인증 완료", HttpStatus.OK),
	CREATE_CARD("카드 생성 완료", HttpStatus.CREATED);

	private final String message;
	private final HttpStatus httpStatus;

	ResponseEnum(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
