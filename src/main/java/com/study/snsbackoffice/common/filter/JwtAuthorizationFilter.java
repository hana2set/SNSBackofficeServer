package com.study.snsbackoffice.common.filter;

import com.study.snsbackoffice.common.util.JwtUtil;
import com.study.snsbackoffice.common.entity.RefreshToken;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import com.study.snsbackoffice.user.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessTokenValue = jwtUtil.getJwtFromHeader(req, "Access");
        String refreshTokenValue = jwtUtil.getJwtFromHeader(req, "Refresh");

        if (StringUtils.hasText(accessTokenValue)) {
            // JWT 토큰 substring
            // 만료되었을 때 response 반환해줘야 한다. -> 미구현
            if (jwtUtil.validateToken(accessTokenValue)) {
                Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);
                try {
                    setAuthentication(info.getSubject());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return;
                }
            } else {
                if (StringUtils.hasText(refreshTokenValue)) {
                    if (validateRefreshToken(refreshTokenValue)) {
                        Claims info = jwtUtil.getUserInfoFromToken(refreshTokenValue);
                        String accessToken = jwtUtil.createToken(info.getSubject(), UserRoleEnum.valueOf(info.get(JwtUtil.AUTHORIZATION_KEY, String.class)), "Access");
                        res.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
                        try {
                            setAuthentication(info.getSubject());
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            return;
                        }
                    }
                }
            }
        }
        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private boolean validateRefreshToken(String refreshTokenValue) {
        List<RefreshToken> refreshToken = refreshTokenRepository.findAll();
        for (RefreshToken token : refreshToken) {
            if (token.getRefreshToken().equals(refreshTokenValue)) {
                return true;
            }
        }
        return false;
    }
}
