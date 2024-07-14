package com.nbcamp.b4trello.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateResponseDto {
    private String nickname;

    @Builder
    public UserUpdateResponseDto(String nickname) {
        this.nickname = nickname;
    }
}
