package com.study.snsbackoffice.user.dto;

import lombok.Getter;

@Getter
public class PasswordRequestDto {
    private String previousPassword;
    private String password;
}
