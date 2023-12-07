package com.study.snsbackoffice.common.entity;

import com.study.snsbackoffice.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "refreshtoken")
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiry;

    public RefreshToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public RefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }
}