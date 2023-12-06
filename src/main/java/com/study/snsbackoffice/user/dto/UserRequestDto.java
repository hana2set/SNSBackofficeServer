package com.study.snsbackoffice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank
    private String nickname;
    @Email
    @NotBlank
    private String email;
    private String description;
}
