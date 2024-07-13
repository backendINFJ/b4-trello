package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.CommonResponse;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.dto.UserRequestDTO;
import com.nbcamp.b4trello.dto.UserResponseDTO;
import com.nbcamp.b4trello.dto.UserResponseDto;
import com.nbcamp.b4trello.dto.UserUpdateRequestDTO;
import com.nbcamp.b4trello.dto.UserUpdateResponseDTO;
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
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    /**
     * 유저 생성
     * @param userDto
     */
    @PostMapping("/")
    public ResponseEntity<CommonResponse<UserResponseDto>> createUser(@Valid @RequestBody UserResponseDto userDto) {
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
    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserUpdateResponseDTO>> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserUpdateRequestDTO updateDTO, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        UserUpdateResponseDTO userUpdateDTO = userService.updateUser(userId, updateDTO, userDetails.getUser());

        CommonResponse<UserUpdateResponseDTO> response = CommonResponse.<UserUpdateResponseDTO>builder()
                .status(ResponseEnum.UPDATE_USER)
                .data(userUpdateDTO).build();
        return ResponseEntity.status(ResponseEnum.UPDATE_USER.getHttpStatus()).body(response);
    }
    /**
     * 유저 삭제
     * @param userId
     * @param userDetails
     */
    @PostMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.deleteUser(userId, userDetails.getUser());
    }
}
