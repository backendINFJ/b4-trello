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
	//auth error
	AUTH_BAD_TOKEN("잘못된 토큰입니다."),
	AUTH_BAD_ACCESS("잘못된 엑세스 토큰입니다."),
	LOGIN_FAILED("로그인 실패"),
	PASSWORD_BAD_REQUEST("잘못된 비밀번호입니다."),
	INVALID_TOKEN("검증되지 않은 토큰"),
	EXPIRED_TOKEN("만료된 토큰"),
	JWT_CLAIMS_EMPTY("토큰 정보가 없습니다."),
	//board error

	//column error

	//card error
	CARD_NOT_FOUND("카드를 찾을 수 없습니다.");

	private final String message;

}
