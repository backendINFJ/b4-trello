package com.nbcamp.b4trello.config;

import com.nbcamp.b4trello.exception.CustomAccessDeniedHandler;
import com.nbcamp.b4trello.exception.CustomAuthenticationEntryPoint;
import com.nbcamp.b4trello.jwt.JwtAuthenticationFilter;
import com.nbcamp.b4trello.jwt.JwtAuthorizationFilter;
import com.nbcamp.b4trello.jwt.JwtProvider;
import com.nbcamp.b4trello.repository.RefreshTokenRepository;
import com.nbcamp.b4trello.repository.UserRepository;
import com.nbcamp.b4trello.service.AuthService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthService authService;

    public SecurityConfig(JwtProvider jwtProvider, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, AuthenticationConfiguration authenticationConfiguration, AuthService authService) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.authenticationConfiguration = authenticationConfiguration;
        this.authService = authService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userRepository, authenticationManager(), refreshTokenRepository);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/login", "/auth/reissue", "/users").permitAll()
                .anyRequest().authenticated()
            )
            .cors(Customizer.withDefaults()) // CORS 설정 추가
            .exceptionHandling(auth -> {
                auth.accessDeniedPage("/forbidden");
                auth.accessDeniedHandler(new CustomAccessDeniedHandler());
                auth.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
            })
            .logout(auth -> auth
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(authService)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }
}