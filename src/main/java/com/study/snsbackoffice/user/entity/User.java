package com.study.snsbackoffice.user.entity;

import com.study.snsbackoffice.common.entity.Timestamped;
import com.study.snsbackoffice.follow.entity.Follow;
import com.study.snsbackoffice.user.dto.AdminUserRequestDto;
import com.study.snsbackoffice.user.dto.UserRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    private List<String> beforePassword = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> followerList;

    @OneToMany(mappedBy = "follower")
    private List<Follow> followingList;

    @Column(nullable = false)
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER; //기본값 user

    public User(String username, String password, String email, String nickName) {
        this.username =  username;
        this.password = password;
        this.email = email;
        this.nickname = nickName;
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
        if (beforePassword.size() > 3) {
            beforePassword.remove(0);
        }
        this.beforePassword.add(password);
        this.password = password;
    }

    public void ban() {
        this.isBanned = true;
        this.unbannedAt = null;
    }
}
