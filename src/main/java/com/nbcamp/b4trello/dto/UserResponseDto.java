package com.nbcamp.b4trello.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;

    @Builder
    public UserResponseDto(String username) {
        this.username = username;
    }
}
