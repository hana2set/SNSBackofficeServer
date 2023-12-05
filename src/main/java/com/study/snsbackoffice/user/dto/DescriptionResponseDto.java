package com.study.snsbackoffice.user.dto;

import lombok.Getter;

@Getter
public class DescriptionResponseDto {
    private String nickname;
    private String description;

    public DescriptionResponseDto(String nickname, String description) {
        this.nickname = nickname;
        this.description = description;
    }
}
