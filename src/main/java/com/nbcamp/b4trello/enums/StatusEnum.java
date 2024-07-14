package com.nbcamp.b4trello.enums;

public enum StatusEnum {
    ACTIVE("Active"),
    VERYFICATION("Verify"),
    DENIED("Denied"),
    ROLE_USER("Role User");

    private final String userStatus;
    StatusEnum(String userStatus) {
        this.userStatus = userStatus;
    }
}
