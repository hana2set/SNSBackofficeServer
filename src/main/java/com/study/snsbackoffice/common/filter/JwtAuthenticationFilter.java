package com.study.snsbackoffice.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.snsbackoffice.common.refreshToken.RefreshTokenService;
import com.study.snsbackoffice.common.util.JwtUtil;
import com.study.snsbackoffice.user.dto.LoginRequestDto;
import com.study.snsbackoffice.common.entity.RefreshToken;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import com.study.snsbackoffice.common.refreshToken.RefreshTokenRepository;
import com.study.snsbackoffice.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final int ALLOW_LOGIN_ATTEMPT_COUNT = 4;
    private final int LOGIN_BAN_MINUTES = 30;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            log.info("로그인 시도 :" + requestDto.getUsername());

            try {
                return getAuthenticationManager().authenticate(
                        new UsernamePasswordAuthenticationToken(
                                requestDto.getUsername(),
                                requestDto.getPassword(),
                                null
                        )
                );
            } catch (BadCredentialsException e) {
                setFailInfoInUser(requestDto.getUsername());
                throw e;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String username = user.getUsername();
        UserRoleEnum role = user.getRole();

        user.resetLoginCount();
        userRepository.save(user);

        String accessToken = jwtUtil.createToken(username, role);
        String refreshToken = refreshTokenService.createRefreshToken(user);
        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }

    private void setFailInfoInUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

        if (user.getLoginFailCount() >= ALLOW_LOGIN_ATTEMPT_COUNT) {
            user.resetLoginCount();
            user.setUnbannedAt(LocalDateTime.now().plusMinutes(LOGIN_BAN_MINUTES));
            user.setIsBanned(true);
        } else {
            user.updateLoginFailInfo();
        }

        userRepository.save(user);

    }
}
