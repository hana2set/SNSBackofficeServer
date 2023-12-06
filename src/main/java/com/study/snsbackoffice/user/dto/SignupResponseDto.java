package com.study.snsbackoffice.user.dto;

import com.study.snsbackoffice.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private long userId;
    public SignupResponseDto(User user) {
        this.userId = user.getId();
    }
}
