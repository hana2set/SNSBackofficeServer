package com.study.snsbackoffice.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PasswordRequestDto {
    @NotBlank
    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*()\\-_=+\\|\\[\\]{};:'\",.<>/?]*")
    private String previousPassword;
    @NotBlank
    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*()\\-_=+\\|\\[\\]{};:'\",.<>/?]*")
    private String password;
}
