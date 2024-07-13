package com.nbcamp.b4trello.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDTO {
    @NotBlank(message = "필수로 입력해야됩니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).{8,15}$", message = "비밀번호는 특수문자포함 최소 8자, 최대 15자입니다.")
    private String password;
    @NotBlank(message = "필수로 입력해야됩니다.")
    private String nickname;

    @Builder
    public UserUpdateRequestDTO(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }
}
