package com.it.vh.common.util.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {

    private final String tokenType;
    private final String token;
}
