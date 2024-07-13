package com.nbcamp.b4trello.enums;

public enum StatusEnum {
    ACTIVE("Active"),
    VERYFICATION("Verify"),
    DENIED("Denied");

    private final String userStatus;
    StatusEnum(String userStatus) {
        this.userStatus = userStatus;
    }
}
