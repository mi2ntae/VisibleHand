package com.it.vh.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2TokenInfo {
    private String token_type = "bearer";
    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private Long refresh_token_expires_in;
}
