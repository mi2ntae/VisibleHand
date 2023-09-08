package com.it.vh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionList {
    UNKNOWN("알 수 없는 오류가 발생하였습니다."),
    NON_EXIST_USER_ID("존재하지 않는 회원입니다."),
    NON_EXIST_SCRAP_ID("존재하지 않는 스크랩입니다."),
    AUTHENTICATION_ACCESS_FORBIDDEN("현재 계정의 접근으로는, 해당 기능을 사용할 수 없습니다.");
    
    private String message;
}
