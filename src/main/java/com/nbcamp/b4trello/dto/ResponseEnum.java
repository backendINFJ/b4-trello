package com.nbcamp.b4trello.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

	CREATE_CARD("카드 생성 완료", HttpStatus.CREATED),
	GET_CARD("카드 조회 완료", HttpStatus.OK),
	UPDATE_CARD("카드 수정 완료", HttpStatus.OK),
	DELETE_CARD("카드 삭제 완료", HttpStatus.OK),
	ERROR("에러가 발생했습니다", HttpStatus.BAD_REQUEST), // 추가
	SUCCESS(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
	CREATED(HttpStatus.CREATED, "리소스가 성공적으로 생성되었습니다."),
	NO_CONTENT(HttpStatus.NO_CONTENT, "리소스가 성공적으로 삭제되었습니다."),
	CREATE_BOARD("보드 생성 완료", HttpStatus.CREATED),
	READ_BOARD("보드 전체조회 완료",HttpStatus.OK),
	UPDATE_BOARD("보드 수정 완료",HttpStatus.OK),
	DELETE_BOARD("보드 삭제 완료",HttpStatus.OK),
	INVITE_USER("유저 초대 완료",HttpStatus.OK),
	CREATE_USER("회원 가입 완료", HttpStatus.CREATED),
	UPDATE_USER("정보 변경 완료", HttpStatus.OK),
	USER_LOGOUT("로그아웃 완료", HttpStatus.OK),
	SEND_MAIL("메일전송 완료", HttpStatus.OK),
	ACCESS_MAIL("메일 인증 완료", HttpStatus.OK),
	DELETE_USER("회원탈퇴 완료", HttpStatus.OK),
	ACCESS_LOGIN("로그인 성공", HttpStatus.OK),
	CHARACTER_ENCODING("UTF-8", HttpStatus.OK),
	CREATE_COMMENT("댓글 생성 완료",HttpStatus.CREATED),
	GET_COMMENT("댓글 조회 완성", HttpStatus.OK);

	private final String message;
	private final HttpStatus httpStatus;

	ResponseEnum(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
