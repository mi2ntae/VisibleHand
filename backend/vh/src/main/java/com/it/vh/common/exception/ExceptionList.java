package com.it.vh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionList {
    UNKNOWN("알 수 없는 오류가 발생하였습니다."),
    NON_EXIST_USER_ID("존재하지 않는 회원입니다.");

    private String message;
}
