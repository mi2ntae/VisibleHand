package com.it.vh.user.api.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDto {

    private UserProfile user;
    private TokenInfo token;

    @Getter
    @Builder
    public static class UserProfile {

        private Long userId;
        private String nickname;
        private String statusMsg;
        private String profileImg;
        private String snsEmail;
    }

    @Getter
    @Builder
    public static class TokenInfo {

        @Builder.Default
        private String type = "Bearer";
        private String accessToken;
        private String refreshToken;
    }
}
