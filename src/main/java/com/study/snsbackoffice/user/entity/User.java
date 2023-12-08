package com.study.snsbackoffice.user.entity;

import com.study.snsbackoffice.common.entity.Timestamped;
import com.study.snsbackoffice.follow.entity.Follow;
import com.study.snsbackoffice.common.util.StringListConverter;
import com.study.snsbackoffice.like.entity.PostLikes;
import com.study.snsbackoffice.user.dto.AdminUserRequestDto;
import com.study.snsbackoffice.user.dto.UserRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "description")
    private String desc;

    private Long loginFailCount = 0L;

    private Boolean isBanned = false;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime unbannedAt;

    @Convert(converter = StringListConverter.class)
    private List<String> beforePassword = new ArrayList<>(); // 지금 암호 + 기존 3개암호

    @OneToMany(mappedBy = "following")
    private List<Follow> followerList;

    @OneToMany(mappedBy = "follower")
    private List<Follow> followingList;

    @Column(nullable = false)
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER; //기본값 user


    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final int MAX_BEFORE_PASSWORD_SIZE = 4;

    private Long kakaoId;

    @OneToMany(mappedBy = "user")
    private List<PostLikes> postLikes = new ArrayList<>();

    public User(String username, String password, String email, String nickName) {
        this.username =  username;
        this.password = password;
        this.email = email;
        this.nickname = nickName;
    }

    public User(String nickname, String password, String email, UserRoleEnum userRoleEnum, Long kakaoId) {
        this.nickname = nickname;
        this.username = nickname;
        this.password = password;
        this.email = email;
        this.role = userRoleEnum;
        this.kakaoId = kakaoId;
    }

    public void update(UserRequestDto requestDto) {
        if(requestDto.getDescription() != null)
            this.desc = requestDto.getDescription();
        if(requestDto.getEmail() != null)
            this.email = requestDto.getEmail();
    }

    public void update(AdminUserRequestDto requestDto) {
        this.desc = requestDto.getDescription();
        this.email = requestDto.getEmail();
        this.nickname = requestDto.getNickname();
        this.role = requestDto.getRole();
    }

    public Long updateLoginFailInfo() {
        if (loginFailCount == null) {
            return loginFailCount = 1L;
        }
        return loginFailCount++;
    }

    public void resetLoginCount() {
        this.loginFailCount = 0L;
    }

    public void descUpdate(String desc) {
        this.desc = desc;
    }

    public void updatePassword(String password) {
        if (beforePassword.size() == MAX_BEFORE_PASSWORD_SIZE) {
            beforePassword.remove(0);
        } else if (beforePassword.size() > MAX_BEFORE_PASSWORD_SIZE) {
            beforePassword = beforePassword.subList(beforePassword.size() - MAX_BEFORE_PASSWORD_SIZE + 1, beforePassword.size());
        }
        this.beforePassword.add(password);
        this.password = password;
    }

    public void ban() {
        this.isBanned = true;
        this.unbannedAt = null;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
