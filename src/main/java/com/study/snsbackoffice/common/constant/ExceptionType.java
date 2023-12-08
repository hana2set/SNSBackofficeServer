package com.study.snsbackoffice.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * {@code GlobalCustomException} 파라미터용 예외 처리 모음 상수 <p>
 *
 * 메세지에 변수 추가 시 <string>{number}</string>로 표기. <p>
 * map으로 처리할 경우 - <string>{variable}</string>로 표기. (미구현) <p>
 * 다국어 - message 대신 type Code 추가 후 DB에 넣어서 조회? (미구현)
* */
@Getter
@RequiredArgsConstructor
public enum ExceptionType {
      
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다. 다시 한번 내용을 확인해주세요."),
    FIELD_VALIDATION(HttpStatus.BAD_REQUEST, "{0} 필드 : {1}"),  
    WRONG_PREVIOUS_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    EXISTING_PREVIOUS_PASSWORD(HttpStatus.BAD_REQUEST,"사용한 적이 있던 비밀번호입니다. 다른 비밀번호를 사용해주세요."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원 번호입니다.: {0}"),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "중복된 사용자가 존재합니다."),
    BAN_USER(HttpStatus.FORBIDDEN, "차단된 사용자입니다. 관리자에게 문의해주세요."),
    BAN_USER_TEMP(HttpStatus.FORBIDDEN, "일시적으로 접근이 제한된 사용자입니다. {0} 이후에 시도해주세요."),

    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 재인증이 필요합니다."),
    REFRESH_TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "토큰 정보가 없습니다."),
  
    SELF_FOLLOW(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우 할 수 없습니다."),
    ALREADY_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우 한 사용자입니다."),
    NOT_EXIST_FOLLOW(HttpStatus.BAD_REQUEST, "팔로우 한 기록이 없습니다."),

    NOT_EXIST_POST(HttpStatus.BAD_REQUEST, "포스트가 존재하지 않습니다."),
    ONLY_AUTHOR_ACCESS(HttpStatus.BAD_REQUEST, "작성자만 수정/삭제 가능합니다."),

    NOT_EXIST_COMMENT(HttpStatus.BAD_REQUEST, "댓글이 존재하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일 입니다.");


    private final HttpStatus status;
    private final String message;
}
