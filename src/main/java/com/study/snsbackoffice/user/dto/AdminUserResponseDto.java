package com.study.snsbackoffice.user.dto;

import com.study.snsbackoffice.common.entity.Timestamped;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdminUserResponseDto extends Timestamped {
    private String username;
    private String email;
    private String nickname;
    private String description;
    private Long loginFailCount;
    private Boolean isBanned;
    private List<String> beforePassword;
    private UserRoleEnum role;


    public AdminUserResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.description = user.getDesc();
        this.loginFailCount = user.getLoginFailCount();
        this.isBanned = user.getIsBanned();
        this.beforePassword = user.getBeforePassword();
        this.role = user.getRole();
    }
}
