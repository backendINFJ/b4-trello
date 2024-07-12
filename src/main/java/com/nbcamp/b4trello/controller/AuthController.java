package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.dto.TokenDTO;
import com.nbcamp.b4trello.jwt.JwtEnum;
import com.nbcamp.b4trello.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    /**
     * 리프레시 토큰을 통한 엑세스 리프레쉬 토큰 재발급 controller
     *
     * @param request  헤더의 토큰
     * @param response 새로운 토큰 세팅
     * @return 200 ok
     */

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request,
            HttpServletResponse response) {
        String refreshToken = request.getHeader(JwtEnum.REFRESH_TOKEN.getValue());
        TokenDTO token = authService.reissue(refreshToken);
        response.setHeader(JwtEnum.ACCESS_TOKEN.getValue(), token.getAccessToken());
        response.setHeader(JwtEnum.REFRESH_TOKEN.getValue(), token.getRefreshToken());
        return ResponseEntity.ok("재발급완료");
    }

    /**
     * 로그아웃 controller 시큐리티 config의 매서드 오출
     *
     * @param request
     * @param response
     * @param authentication
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        authService.logout(request, response, authentication);

        return ResponseEntity.status(ResponseEnum.USER_LOGOUT.getHttpStatus()).body(ResponseEnum.USER_LOGOUT.getMessage());
    }


}
