package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.UserRequestDTO;
import com.nbcamp.b4trello.dto.UserUpdateDTO;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import com.nbcamp.b4trello.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDTO userDto) {
        return userService.createUser(userDto);
    }
    /**
     * 유저 수정
     * @param userId
     * @param userDetails
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserUpdateDTO updateDTO, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        return userService.updateUser(userId, updateDTO, userDetails.getUser());
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
