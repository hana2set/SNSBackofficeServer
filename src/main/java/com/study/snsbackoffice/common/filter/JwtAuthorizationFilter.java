package com.study.snsbackoffice.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.common.refreshToken.RefreshTokenService;
import com.study.snsbackoffice.common.util.JwtUtil;
import com.study.snsbackoffice.common.entity.RefreshToken;
import com.study.snsbackoffice.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    private final ObjectMapper objectMapper;

    private final RefreshTokenService refreshTokenService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, ObjectMapper objectMapper, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessTokenValue = jwtUtil.getJwtFromHeader(req);
        String refreshTokenValue = req.getHeader(jwtUtil.REFRESH_TOKEN_HEADER);

        try {
            if (StringUtils.hasText(accessTokenValue)) {
                // JWT 토큰
                if (jwtUtil.validateToken(accessTokenValue)) {
                    Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);

                    setAuthentication(info.getSubject());
                } else if (StringUtils.hasText(refreshTokenValue)) {
                    RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenValue).orElseThrow(
                            () -> new GlobalCustomException(ExceptionType.REFRESH_TOKEN_NOT_EXIST)
                    );

                    //TODO Access 토큰과 비교해 부정한 사용자인지 확인하면 더 좋을듯

                    //유효 검사
                    refreshToken = refreshTokenService.verifyExpiration(refreshToken);

                    // 유효하면 JWT 토큰 재발급
                    User user = refreshToken.getUser();
                    String accessToken = jwtUtil.createToken(user.getUsername(), user.getRole());
                    res.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);

                    setAuthentication(user.getUsername());
                }
            }
        } catch (GlobalCustomException e) {
            res.setContentType("application/json; charset=UTF-8");
            res.setStatus(e.getStatus().value());
            res.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
            return;
        } catch (Exception e) {
            log.error(e.getMessage());
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

}
