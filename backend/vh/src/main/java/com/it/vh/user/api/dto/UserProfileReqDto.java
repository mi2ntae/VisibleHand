package com.it.vh.user.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileReqDto {

    private Profile profile;
    private String profileImg;
    private String snsEmail;

    @Getter
    @Builder
    public static class Profile {
        private String nickname;
        private String statusMsg;
    }
}
