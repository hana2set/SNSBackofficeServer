package com.study.snsbackoffice.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private String nickname;
    private String email;
    private String description;
}
