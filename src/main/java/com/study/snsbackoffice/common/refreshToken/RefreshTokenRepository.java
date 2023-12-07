package com.study.snsbackoffice.common.refreshToken;

import com.study.snsbackoffice.common.entity.RefreshToken;
import com.study.snsbackoffice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
//    RefreshToken findByUsername(String username);
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    int deleteByUser(User user);
}
