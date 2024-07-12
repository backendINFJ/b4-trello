package com.nbcamp.b4trello.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDTO {
    private String username;

    @Builder
    public UserResponseDTO(String username) {
        this.username = username;
    }
}
