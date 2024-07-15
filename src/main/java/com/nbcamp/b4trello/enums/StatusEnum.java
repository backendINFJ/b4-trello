package com.nbcamp.b4trello.enums;

public enum StatusEnum {
    ACTIVE("Active"), // 유저 생성시
    VERYFICATION("Verify"), // 이메일 인증 완료시
    DENIED("Denied"), // 탈퇴시
    ROLE_USER("Role User"); // 시큐리티 역할

    private final String userStatus;
    StatusEnum(String userStatus) {
        this.userStatus = userStatus;
    }
}
