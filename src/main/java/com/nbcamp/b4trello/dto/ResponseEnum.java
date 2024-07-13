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
	DELETE_BOARD("보드 삭제 완료",HttpStatus.OK),
	INVITE_USER("유저 초대 완료",HttpStatus.OK),
	CREATE_USER("회원 가입 완료", HttpStatus.CREATED),
	UPDATE_USER("정보 변경 완료", HttpStatus.OK),
	USER_LOGOUT("로그아웃 완료", HttpStatus.OK),
	SEND_MAIL("메일전송 완료", HttpStatus.OK),
	ACCESS_MAIL("메일 인증 완료", HttpStatus.OK);

	private final String message;
	private final HttpStatus httpStatus;
}
