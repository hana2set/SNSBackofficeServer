package com.study.snsbackoffice.user.repository;

import com.study.snsbackoffice.common.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUsername(String username);
}
