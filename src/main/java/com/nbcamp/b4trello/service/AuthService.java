package com.nbcamp.b4trello.service;

import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.dto.TokenDTO;
import com.nbcamp.b4trello.entity.RefreshToken;
import com.nbcamp.b4trello.jwt.JwtEnum;
import com.nbcamp.b4trello.jwt.JwtUtil;
import com.nbcamp.b4trello.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 로그인 인증 관련 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements LogoutHandler {
    /**
     * 관련 클래스 호출
     */
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    /**
     * 토큰 재발급 메서드
     * @param refreshToken
     * @return tokenDto
     */
    @Transactional
    public TokenDTO reissue(String refreshToken) {
        Optional<RefreshToken> token = refreshTokenRepository.findByRefreshToken(refreshToken);
        if(token!=null && !token.get().getRefreshToken().equals(refreshToken)){
            throw new IllegalArgumentException(String.valueOf(ErrorMessageEnum.AUTH_BAD_TOKEN));
        }
        Authentication authentication = jwtUtil.getAuthentication(refreshToken.substring(7));
        TokenDTO tokenDto = jwtUtil.createToken(authentication);
        token.get().updateRefreshToken(tokenDto.getRefreshToken());
        return tokenDto;
    }

    /**
     * 로그아웃 메서드
     * @param request
     * @param response
     * @param authentication
     */
    @Transactional
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response , Authentication authentication) {
        String authHeader = request.getHeader(JwtEnum.ACCESS_TOKEN.getValue());

        if (authHeader == null && !authHeader.startsWith(JwtEnum.GRANT_TYPE.getValue())) {
            throw new IllegalArgumentException(String.valueOf(ErrorMessageEnum.AUTH_BAD_ACCESS));
        }
        String accessToken = authHeader.substring(7);
        String username = jwtUtil.getUsername(accessToken);
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(username).orElse(null);
        if(refreshToken == null) {
            throw new IllegalArgumentException(String.valueOf(ErrorMessageEnum.AUTH_BAD_TOKEN));
        }
        refreshTokenRepository.delete(refreshToken);
    }



}
