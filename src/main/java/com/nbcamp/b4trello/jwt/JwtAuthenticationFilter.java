package com.nbcamp.b4trello.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcamp.b4trello.dto.AuthRequestDto;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.dto.TokenDto;
import com.nbcamp.b4trello.entity.RefreshToken;
import com.nbcamp.b4trello.repository.RefreshTokenRepository;
import com.nbcamp.b4trello.repository.UserRepository;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Order(Integer.MIN_VALUE)
@Component
@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationFilter(JwtProvider jwtUtil, UserRepository userRepository, AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/auth/login");
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream == null || inputStream.available() == 0) {
                throw new IOException("Request input stream is empty");
            }

            ObjectMapper mapper = new ObjectMapper();
            AuthRequestDto authRequest = mapper.readValue(inputStream, AuthRequestDto.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            logger.error("Failed to parse authentication request body", e);
            throw new RuntimeException("Failed to parse authentication request body", e);
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
        validateToken(refreshToken);
        refreshTokenRepository.save(refreshToken);
        response.setHeader(JwtEnum.ACCESS_TOKEN.getValue(), jwtToken.getAccessToken());
        response.setHeader(JwtEnum.REFRESH_TOKEN.getValue(), jwtToken.getRefreshToken());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(
            ResponseEnum.ACCESS_LOGIN.getMessage()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ResponseEnum.LOGIN_FAILED.getMessage());
    }

    public void validateToken(RefreshToken token){
        if (refreshTokenRepository.existsByUsername(token.getUsername())) {
            refreshTokenRepository.delete(refreshTokenRepository.findByUsername(token.getUsername()).get());
        }
    }
}