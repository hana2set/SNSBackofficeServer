package com.study.snsbackoffice.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.snsbackoffice.common.filter.JwtAuthenticationFilter;
import com.study.snsbackoffice.common.filter.JwtAuthorizationFilter;
import com.study.snsbackoffice.common.filter.UserDetailsServiceImpl;
import com.study.snsbackoffice.common.handler.CustomAuthenticationEntryPoint;
import com.study.snsbackoffice.common.refreshToken.RefreshTokenService;
import com.study.snsbackoffice.common.util.JwtUtil;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import com.study.snsbackoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, userRepository, refreshTokenService);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService, objectMapper, refreshTokenService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/", "/error/**").permitAll() // 메인페이지, error 페이지 접근 허용
                        .requestMatchers("/api/users/signup", "/api/users/login").permitAll() // 회원가입, 로그인 페이지 접근 허용
                        .requestMatchers("/api/admin/**").hasRole(UserRoleEnum.ADMIN.name())
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        http.exceptionHandling(
                (exception) ->
                        exception
                                .authenticationEntryPoint(authenticationEntryPoint()) // 토큰인증 실패시
                                .accessDeniedPage("/error/403") // 권한없는 페이지 접근 시 (admin)
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }
}
