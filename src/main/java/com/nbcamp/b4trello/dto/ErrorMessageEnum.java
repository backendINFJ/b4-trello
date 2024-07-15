package com.nbcamp.b4trello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageEnum {

	//user error
	USER_NOT_FOUND("유저를 찾을수 없습니다."),
	USER_DENIND("탈퇴한 유저입니다."),
	ALREADY_USER("이미 있는 사용자"),
	PRIVATE_USER("다른 사용자의 권한입니다."),
	SAME_PASSWORD("비밀번호가 중복되었습니다."),
	USER_NOT_AUTHORIZED("사용자에게 권한이 없습니다."),

	//auth error
	AUTH_BAD_TOKEN("잘못된 토큰입니다."),
	AUTH_BAD_ACCESS("잘못된 엑세스 토큰입니다."),
	LOGIN_FAILED("로그인 실패"),
	PASSWORD_BAD_REQUEST("잘못된 비밀번호입니다."),
	INVALID_TOKEN("검증되지 않은 토큰"),
	EXPIRED_TOKEN("만료된 토큰"),
	JWT_CLAIMS_EMPTY("토큰 정보가 없습니다."),
	MAIL_BAD_REQUEST("잘못된 인증번호입니다."),
	ALREADY_MAIL_ACCESS("이미 인증을 받은 계정입니다."),
	//board error
	BOARD_NOT_FOUND("보드를 찾을 수 없습니다."),
	BOARD_ACCESS_DENIED("보드 접근이 거부되었습니다."),
	BOARD_NOT_DATA("보드 생성에 필요한 필수 데이터가 없습니다."),  // entity에서 예외처리 고민중 -> @NotBlank
	BOARD_NOT_UNAUTHORIZED("권한에 맞지않는 사용자 입니다."),
	BOARD_NOT_INVIATION("이미 초대된 사용자 입니다"),
	BOARD_NOT_INVITED("초대할 권한이 없습니다"),
	BOARD_NOT_FAILEINVIATED("권한이 없거나,보드가 존재하지 않습니다"),

	// Column errors
	COLUMN_NOT_FOUND("컬럼을 찾을 수 없습니다."),
	COLUMN_TITLE_ALREADY_EXISTS("컬럼 제목이 이미 존재합니다."),
	COLUMN_SEQUENCE_MISMATCH("컬럼 순서가 일치하지 않습니다."),
	COLUMN_NOT_SEQUENCE("컬럼 순서를 찾을 수 없습니다."),

	// Card errors
	CARD_NOT_FOUND("카드를 찾을 수 없습니다.");

	private final String message;

}
