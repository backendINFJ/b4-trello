package com.nbcamp.b4trello.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateResponseDTO {
    private String nickname;

    @Builder
    public UserUpdateResponseDTO(String nickname) {
        this.nickname = nickname;
    }
}
