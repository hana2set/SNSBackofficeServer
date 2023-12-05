package com.study.snsbackoffice.user.entity;

import com.study.snsbackoffice.common.entity.Timestamped;
import com.study.snsbackoffice.user.dto.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String email, UserRoleEnum role, String nickName) {
        this.username =  username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.nickname = nickName;
    }
    public void update(UserUpdateRequestDto requestDto) {
        if(requestDto.getDescription() != null)
            this.desc = requestDto.getDescription();
        if(requestDto.getEmail() != null)
            this.email = requestDto.getEmail();
    }

    public void descUpdate(String desc) {
        this.desc = desc;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
