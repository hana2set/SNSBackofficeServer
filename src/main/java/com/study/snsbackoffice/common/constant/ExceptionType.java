package com.study.snsbackoffice.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치 하지 않습니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원 번호입니다.: {0} , {1}"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 Email 입니다."),
    SELF_FOLLOW(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우 할 수 없습니다."),
    ALREADY_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우 한 사용자입니다."),
    NOT_EXIST_FOLLOW(HttpStatus.BAD_REQUEST, "팔로우 한 기록이 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;


}
