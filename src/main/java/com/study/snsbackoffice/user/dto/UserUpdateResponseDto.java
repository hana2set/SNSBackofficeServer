package com.study.snsbackoffice.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateResponseDto {
    private String nickname;
    private String email;
    private String description;

    public UserUpdateResponseDto(UserUpdateRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
        this.description = requestDto.getDescription();
    }
}
