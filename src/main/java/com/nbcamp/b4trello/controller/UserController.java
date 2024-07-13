package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.CommonResponse;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.dto.UserRequestDto;
import com.nbcamp.b4trello.dto.UserResponseDto;
import com.nbcamp.b4trello.dto.UserUpdateRequestDto;
import com.nbcamp.b4trello.dto.UserUpdateResponseDto;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import com.nbcamp.b4trello.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;
    /**
     * 유저 생성
     * @param userDto
     */
    @PostMapping()
    public ResponseEntity<CommonResponse<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto userDto) {
        UserResponseDto userResponseDTO = userService.createUser(userDto);
        CommonResponse<UserResponseDto> response = CommonResponse.<UserResponseDto>builder()
                .status(ResponseEnum.CREATE_USER)
                .data(userResponseDTO).build();
        return ResponseEntity.status(ResponseEnum.CREATE_USER.getHttpStatus()).body(response);
    }
    /**
     * 유저 수정
     * @param userId
     * @param userDetails
     */
    @PatchMapping("/{user-id}")
    public ResponseEntity<CommonResponse<UserUpdateResponseDto>> updateUser(@PathVariable("user-id") Long userId, @Valid @RequestBody UserUpdateRequestDto updateDTO, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        UserUpdateResponseDto userUpdateDTO = userService.updateUser(userId, updateDTO, userDetails.getUser());

        CommonResponse<UserUpdateResponseDto> response = CommonResponse.<UserUpdateResponseDto>builder()
                .status(ResponseEnum.UPDATE_USER)
                .data(userUpdateDTO).build();
        return ResponseEntity.status(ResponseEnum.UPDATE_USER.getHttpStatus()).body(response);
    }
    /**
     * 유저 삭제
     * @param userId
     * @param userDetails
     */
    @PostMapping("/delete/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userId, userDetails.getUser());
        return ResponseEntity.status(ResponseEnum.DELETE_USER.getHttpStatus()).body(ResponseEnum.DELETE_USER.getMessage());
    }
}
