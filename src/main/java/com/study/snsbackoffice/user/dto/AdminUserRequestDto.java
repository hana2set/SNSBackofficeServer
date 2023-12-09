package com.study.snsbackoffice.user.dto;

import com.study.snsbackoffice.user.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserRequestDto {
    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*()\\-_=+\\|\\[\\]{};:'\",.<>/?]*")
    private String password;
    @NotBlank
    private String nickname;
    @Email
    @NotBlank
    private String email;
    private String description;
    private UserRoleEnum role;
}
