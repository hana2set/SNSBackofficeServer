package com.study.snsbackoffice.common.refreshToken;

import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.entity.RefreshToken;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    // Refresh Token 만료시간
    @Value("${jwt.secret.refresh_token_expiry_ms}")
    private long REFRESH_TOKEN_TIME;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public String createRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setExpiry(LocalDateTime.ofInstant(Instant.now().plusMillis(REFRESH_TOKEN_TIME), ZoneId.systemDefault()));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiry().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new GlobalCustomException(ExceptionType.REFRESH_TOKEN_EXPIRED);
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(User user) {
        return refreshTokenRepository.deleteByUser(user);
    }
}