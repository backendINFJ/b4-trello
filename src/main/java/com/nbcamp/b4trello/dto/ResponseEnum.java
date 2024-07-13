package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
	CREATE_USER("회원 가입 완료", HttpStatus.CREATED),
	UPDATE_USER("정보 변경 완료", HttpStatus.OK),
	USER_LOGOUT("로그아웃 완료", HttpStatus.OK),
	SEND_MAIL("메일전송 완료", HttpStatus.OK),
	ACCESS_MAIL("메일 인증 완료", HttpStatus.OK),
	DELETE_USER("회원탈퇴 완료", HttpStatus.OK),
	ACCESS_LOGIN("로그인 성공", HttpStatus.OK),
	CHARACTER_ENCODING("UTF-8",HttpStatus.OK),
	CREATE_CARD("카드 생성 완료", HttpStatus.CREATED);

	private final String message;
	private final HttpStatus httpStatus;
}
