package com.it.vh.common.exception;

import com.it.vh.article.domain.exception.NonExistScrapIdException;
import com.it.vh.user.exception.NonExistUserIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NonExistScrapIdException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorResponse> nonExistScrapIdException() {
        log.error("해당 스크랩이 이미 존재하지 않습니다.");
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionList.NON_EXIST_SCRAP_ID);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(AuthenticationAccessForbiddenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ErrorResponse> authenticationAccessForbiddenException() {
        log.error("해당 유저가 아님. 비정상적인 접근");
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionList.AUTHENTICATION_ACCESS_FORBIDDEN);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NonExistUserIdException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorResponse> nonExistUserIdException() {
        log.error("no exist user id");
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionList.NON_EXIST_USER_ID);
        return ResponseEntity.internalServerError().body(errorResponse);
    }
    
    // 아래는 상관 없음. 위에 작성
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorResponse> handlerException(Exception e) {
        log.error("Exception", e);
        ErrorResponse errorResponse = ErrorResponse.of(ExceptionList.UNKNOWN);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}