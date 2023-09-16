package com.it.vh.common.util.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExceptionList {
    ACCESS_DENIED("접근할 수 없습니다."),
    UNAUTHORIZED("인증되지 않은 회원입니다."),

    EXPIRED_TOKEN("만료된 토큰"),
    INVALID_TOKEN("유효하지 않은 토큰"),
    MALFORMED_HEADER("잘못된 형식의 헤더"),

    NOT_MATCHED_TOKEN("토큰 불일치"),
    TOKEN_NOTFOUND("토큰 존재하지 않음"),
    TOKEN_EXCEPTION("토큰 에러");

    private final String message;
}
