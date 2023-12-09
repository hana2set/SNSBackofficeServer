package com.study.snsbackoffice.user.dto;

import com.study.snsbackoffice.common.entity.Timestamped;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdminUserResponseDto {
    private String username;
    private String email;
    private String nickname;
    private String description;
    private Long loginFailCount;
    private Boolean isBanned;
    private UserRoleEnum role;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public AdminUserResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.description = user.getDesc();
        this.loginFailCount = user.getLoginFailCount();
        this.isBanned = user.getIsBanned();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}
