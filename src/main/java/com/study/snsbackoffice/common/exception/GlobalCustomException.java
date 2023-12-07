package com.study.snsbackoffice.common.exception;

import com.study.snsbackoffice.common.constant.ExceptionType;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * 공통 예외처리를 위한 커스텀 예외. <p>
 * 기본적으로 {@code ExceptionType}를 매개변수로 받아 해당하는 {@code HttpStatus}와 메세지를 RuntimeException으로 반환하도록 구성됨 <p>
 * 추가 변수가 없을 경우, 그대로 메세지를 출력.<p>
 * 추가 변수가 있을 경우 받은 순서대로 "{number}"를 치환하여 메세지를 출력.<p> */
public class GlobalCustomException extends RuntimeException {

    private ExceptionType type = ExceptionType.BAD_REQUEST;
    private String message;

    public GlobalCustomException() {
        super(ExceptionType.BAD_REQUEST.getMessage());
    }

    public GlobalCustomException(String message) {
        super(message);
    }

    public GlobalCustomException(ExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }

    public GlobalCustomException(ExceptionType type, String... values) {
        this.type = type;
        this.message = replaceText(type.getMessage(), values);
    }


    public HttpStatus getStatus() {
        return type.getStatus();
    }

    @Override
    public String getMessage() {
        return message == null ? super.getMessage() : message;
    }


    private String replaceText(String text, String... values) {

        String replaceText = text;
        for (int i = 0; i < values.length; i++) {
            replaceText = replaceText.replaceAll("\\{" + i + "\\}" , values[i]);
        }

        return replaceText;
    }


    private String replaceText(String text, Map<String, String> map) {

        String replaceText = text;
        for (String key : map.keySet()) {
            replaceText = replaceText.replaceAll("\\{" + key + "\\}" , map.get(key));
        }

        return replaceText;
    }
}
