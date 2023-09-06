package com.it.vh.common.exception;

import java.util.List;

import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private int code;
    private String message;

    public static ErrorResponse of(ExceptionList exceptionList) {
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exceptionList.getMessage())
                .build();
    }

    public static ErrorResponse of(int code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }
}