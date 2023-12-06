package com.study.snsbackoffice.common.handler;

import com.study.snsbackoffice.common.exception.GlobalCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalCustomException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalCustomException(GlobalCustomException e) {
        log.error(e.getMessage());
        return new ResponseEntity(e.getMessage(), e.getStatus());
    }
}