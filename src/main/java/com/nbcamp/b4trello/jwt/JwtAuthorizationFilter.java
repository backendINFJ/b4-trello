package com.nbcamp.b4trello.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final JwtProvider jwtUtil;


    /**
     * Request Header 에서 토큰 정보 추출
     *
     * @param request
     * @return
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtEnum.ACCESS_TOKEN.getValue());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(
                JwtEnum.GRANT_TYPE.getValue())) {
            return bearerToken.substring(7);
        }
        return null;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        /**
         * 토큰정보추출
         */
        String token = resolveToken((HttpServletRequest) servletRequest);

        /**
         *  validateToken 으로 토큰 유효성 검사
         *  토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
         */
        if (token != null && jwtUtil.validateToken(token)) {
            Authentication authentication = jwtUtil.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}