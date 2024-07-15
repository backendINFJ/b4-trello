package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.KeyDto;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.dto.TokenDto;
import com.nbcamp.b4trello.jwt.JwtEnum;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import com.nbcamp.b4trello.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS}, allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader(JwtEnum.REFRESH_TOKEN.getValue());
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리프레시 토큰이 없습니다.");
        }
        try {
            TokenDto token = authService.reissue(refreshToken);
            response.setHeader(JwtEnum.ACCESS_TOKEN.getValue(), token.getAccessToken());
            response.setHeader(JwtEnum.REFRESH_TOKEN.getValue(), token.getRefreshToken());
            return ResponseEntity.ok("재발급완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            authService.logout(request, response, authentication);
            return ResponseEntity.status(ResponseEnum.USER_LOGOUT.getHttpStatus()).body(ResponseEnum.USER_LOGOUT.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendMail(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.sendMail(userDetails.getUser());
        return ResponseEntity.status(ResponseEnum.SEND_MAIL.getHttpStatus()).body(ResponseEnum.SEND_MAIL.getMessage());
    }

    @PostMapping("/check-mail")
    public ResponseEntity<String> checkMail(@RequestBody KeyDto key, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.checkMail(key.getKey(), userDetails.getUser().getEmail());
        return ResponseEntity.status(ResponseEnum.ACCESS_MAIL.getHttpStatus()).body(ResponseEnum.ACCESS_MAIL.getMessage());
    }
}