package com.study.snsbackoffice.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseInfoDto {
    private String username;
    private String email;
    private String nickname;
    private String description;

    public ResponseInfoDto(String username, String nickname, String email, String description) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.description = description;
    }
}
