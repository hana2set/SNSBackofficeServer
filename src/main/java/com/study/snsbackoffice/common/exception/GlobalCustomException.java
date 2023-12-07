package com.study.snsbackoffice.common.exception;

import com.study.snsbackoffice.common.constant.ExceptionType;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class GlobalCustomException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public GlobalCustomException(ExceptionType type) {
        super(type.getMessage());
        this.status = type.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
