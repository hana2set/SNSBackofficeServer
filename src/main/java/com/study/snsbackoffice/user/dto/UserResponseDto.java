package com.study.snsbackoffice.user.dto;

import com.study.snsbackoffice.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String username;
    private String email;
    private String nickname;
    private String description;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.description = user.getDesc();
    }
}
