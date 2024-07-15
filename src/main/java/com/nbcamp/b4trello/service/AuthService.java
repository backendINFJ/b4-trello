package com.nbcamp.b4trello.service;

import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.dto.TokenDto;
import com.nbcamp.b4trello.dto.UserResponseDto;
import com.nbcamp.b4trello.entity.RefreshToken;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.enums.StatusEnum;
import com.nbcamp.b4trello.jwt.JwtEnum;
import com.nbcamp.b4trello.jwt.JwtProvider;
import com.nbcamp.b4trello.repository.RefreshTokenRepository;
import com.nbcamp.b4trello.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;
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
    private final JwtProvider jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    /**
     * 토큰 재발급 메서드
     * @param refreshToken
     * @return tokenDto
     */
    @Transactional
    public TokenDto reissue(String refreshToken) {
        Optional<RefreshToken> token = refreshTokenRepository.findByRefreshToken(refreshToken);
        if(token.isEmpty() || !token.get().getRefreshToken().equals(refreshToken)){
            throw new IllegalArgumentException(ErrorMessageEnum.AUTH_BAD_TOKEN.getMessage());
        }
        Authentication authentication = jwtUtil.getAuthentication(refreshToken.substring(7));
        TokenDto tokenDto = jwtUtil.createToken(authentication);
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
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(JwtEnum.ACCESS_TOKEN.getValue());

        if (authHeader == null || !authHeader.startsWith(JwtEnum.GRANT_TYPE.getValue())) {
            throw new IllegalArgumentException(ErrorMessageEnum.AUTH_BAD_ACCESS.getMessage());
        }
        String accessToken = authHeader.substring(7);
        String username = jwtUtil.getUsername(accessToken);
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(username).orElse(null);
        if(refreshToken == null) {
            throw new IllegalArgumentException(ErrorMessageEnum.AUTH_BAD_TOKEN.getMessage());
        }
        refreshTokenRepository.delete(refreshToken);
    }

    /**
     * 메일 전송 메서드
     * @param user
     */
    public void sendMail(User user){
        if(user.getStatus() == StatusEnum.VERYFICATION) {
            throw new IllegalArgumentException(ErrorMessageEnum.ALREADY_MAIL_ACCESS.getMessage());
        }
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString().substring(0,7);
        String sub = "인증번호 메일 전송";
        String content = "인증번호 : " + key;
        //mailManager.send(user.getEmail(), sub, content);
        //mockup for mailManager
        log.info("Sending mail to: {}, Subject: {}, Content: {}", user.getEmail(), sub, content);
    }

    /**
     * 메일 인증 코드 검증 메서드
     * @param key
     * @param email
     * @return
     */
    @Transactional
    public UserResponseDto checkMail(String key, String email){
        String insertKey = key; //mockup for key generation
        if (!key.equals(insertKey)){
            throw new IllegalArgumentException(ErrorMessageEnum.MAIL_BAD_REQUEST.getMessage());
        }
        Optional<User> user = userRepository.findByEmail(email);
        user.get().verifiStatus();
        userRepository.save(user.get());
        return UserResponseDto.builder().username(user.get().getUsername()).build();
    }
}