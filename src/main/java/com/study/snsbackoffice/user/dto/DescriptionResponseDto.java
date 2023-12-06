package com.study.snsbackoffice.user.dto;

import lombok.Getter;

@Getter
public class DescriptionResponseDto {
    private String description;

    public DescriptionResponseDto(String description) {
        this.description = description;
    }
}
