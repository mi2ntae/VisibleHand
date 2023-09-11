package com.it.vh.user.api.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * {
 * 	user : {
 * 		userId : 0,
 * 		nickname : "",
 * 		statusMsg : ""
 *        },
 * 	token : ""
 * }
 */
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
        private String type;
        private String accessToken;
        private String refreshToken;
    }
}
