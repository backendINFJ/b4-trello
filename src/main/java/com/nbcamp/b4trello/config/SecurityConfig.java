package com.nbcamp.b4trello.config;

import com.nbcamp.b4trello.exception.CustomAccessDeniedHandler;
import com.nbcamp.b4trello.exception.CustomAuthenticationEntryPoint;
import com.nbcamp.b4trello.jwt.JwtAuthenticationFilter;
import com.nbcamp.b4trello.jwt.JwtAuthorizationFilter;
import com.nbcamp.b4trello.jwt.JwtEnum;
import com.nbcamp.b4trello.jwt.JwtProvider;
import com.nbcamp.b4trello.repository.RefreshTokenRepository;
import com.nbcamp.b4trello.repository.UserRepository;
import com.nbcamp.b4trello.service.AuthService;
import java.util.Arrays;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * SecurityConfig 필드값
     */
    private final JwtProvider jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    /**
     * 생성자 매서드
     */
    public SecurityConfig(JwtProvider jwtUtil,
            AuthenticationConfiguration authenticationConfiguration, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    /**
     * 빈주입
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, userRepository,authenticationManager(authenticationConfiguration),bCryptPasswordEncoder(),
                 refreshTokenRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        configuration.addExposedHeader(JwtEnum.ACCESS_TOKEN.getValue());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Security 인증, 인가 설정
     * 커스텀 필터 설정
     * 서버단 에러 status 핸들러
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthService authService) throws Exception {
        // CSRF 설정
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/auth/login","/auth/reissue","/users").permitAll()


                        // 서버 단에서 에러가 발생시 아래 url이 에러창을 띄워준다
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()

        );
        //필터중 예외 처리
        http.exceptionHandling(auth -> {
            auth.accessDeniedPage("/forbidden");
            auth.accessDeniedHandler(new CustomAccessDeniedHandler());
            auth.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        });

        http.logout(auth -> auth
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(authService)
                .logoutSuccessHandler(
                        (((request, response, authentication) -> SecurityContextHolder.clearContext()))));





        // 필터 관리
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }
}
