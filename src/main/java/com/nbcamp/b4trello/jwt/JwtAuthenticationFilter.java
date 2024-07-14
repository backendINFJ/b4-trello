package com.nbcamp.b4trello.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcamp.b4trello.dto.AuthRequestDto;
import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.dto.TokenDto;
import com.nbcamp.b4trello.entity.RefreshToken;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.enums.StatusEnum;
import com.nbcamp.b4trello.repository.RefreshTokenRepository;
import com.nbcamp.b4trello.repository.UserRepository;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Order(Integer.MIN_VALUE)
@Component
@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;


    public JwtAuthenticationFilter(JwtProvider jwtUtil, UserRepository userRepository, AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bCryptPasswordEncoder, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/auth/login");
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthRequestDto authRequestDto = null;
        try{
            authRequestDto = objectMapper.readValue(request.getInputStream(), AuthRequestDto.class);

            Optional<User> user = userRepository.findByUsername(authRequestDto.getUsername());
            if(null != user) {
                if(user.get().getStatus() == StatusEnum.DENIED){
                    throw new IllegalArgumentException(String.valueOf(ErrorMessageEnum.USER_DENIND));
                }
            }
            if (!bCryptPasswordEncoder.matches(authRequestDto.getPassword(),user.get().getPassword())) {
                throw new IllegalArgumentException(String.valueOf(ErrorMessageEnum.PASSWORD_BAD_REQUEST));
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authRequestDto.getUsername(),authRequestDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        TokenDto jwtToken = jwtUtil.createToken(authResult);
        RefreshToken refreshToken = RefreshToken.builder()
                .username(userDetails.getUsername())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
            vaildateToken(refreshToken);
            refreshTokenRepository.save(refreshToken);
            response.setHeader(JwtEnum.ACCESS_TOKEN.getValue(), jwtToken.getAccessToken());
            response.setHeader(JwtEnum.REFRESH_TOKEN.getValue(), jwtToken.getRefreshToken());
            response.setStatus(ResponseEnum.CHARACTER_ENCODING.getHttpStatus().value());
            // 로그인 성공 메세지 반환
            response.setCharacterEncoding(ResponseEnum.CHARACTER_ENCODING.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    ResponseEnum.ACCESS_LOGIN.getMessage()));

    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
        response.setCharacterEncoding(ResponseEnum.CHARACTER_ENCODING.getMessage());
        response.getWriter().write(ErrorMessageEnum.LOGIN_FAILED.getMessage());
    }
    public void vaildateToken(RefreshToken token){
        if (refreshTokenRepository.existsByUsername(token.getUsername())) {
            refreshTokenRepository.delete(
                    refreshTokenRepository.findByUsername(token.getUsername()).get());
        }
    }
}
