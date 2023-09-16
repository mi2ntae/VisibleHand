package com.it.vh.user.api.dto.auth;

import com.it.vh.common.util.jwt.dto.TokenInfo;
import com.it.vh.user.domain.entity.User;
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

        public static UserProfile from(User user) {
            return UserProfile.builder()
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .statusMsg(user.getStatusMsg())
                    .profileImg(user.getProfileImg())
                    .snsEmail(user.getSnsEmail())
                    .build();
        }
    }
}
