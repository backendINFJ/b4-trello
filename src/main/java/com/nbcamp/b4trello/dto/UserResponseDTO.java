package com.nbcamp.b4trello.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserResponseDTO {
    private String username;

    @Builder
    public UserResponseDTO(String username) {
        this.username = username;
    }
}
