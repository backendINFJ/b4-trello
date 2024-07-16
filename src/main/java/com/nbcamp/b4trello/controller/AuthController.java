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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
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
        TokenDto token = authService.reissue(refreshToken);
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

    /**
     * 메일 전송 controller
     *
     * @param userDetails
     * @return ok
     */
    @PostMapping("/send-mail")
    public ResponseEntity<String> sendMail(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.sendMail(userDetails.getUser());
        return ResponseEntity.status(ResponseEnum.SEND_MAIL.getHttpStatus()).body(ResponseEnum.SEND_MAIL.getMessage());


    }

    /**
     * 이메일 인증 확인 controller
     *
     * @param key               인증 키
     * @param userDetails 유저 이메일
     * @return ok
     */
    @PostMapping("/check-mail")
    public ResponseEntity<String> checkMail(@RequestBody KeyDto key,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.checkMail(key.getKey(), userDetails.getUser().getEmail());
        return ResponseEntity.status(ResponseEnum.ACCESS_MAIL.getHttpStatus()).body(ResponseEnum.ACCESS_MAIL.getMessage());

    }
}
