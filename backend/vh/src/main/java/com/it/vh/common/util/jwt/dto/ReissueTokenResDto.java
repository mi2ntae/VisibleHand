package com.it.vh.common.util.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReissueTokenResDto {

    @Builder.Default
    private String message = "토큰 재발급 완료";
    private TokenInfo tokenInfo;

    public static ReissueTokenResDto of(TokenInfo tokenInfo) {
        return ReissueTokenResDto.builder()
                .tokenInfo(tokenInfo)
                .build();
    }
}
