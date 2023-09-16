package com.it.vh.common.util.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfo {

    @Builder.Default
    private String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
