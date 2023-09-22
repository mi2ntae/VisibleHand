package com.it.vh.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileReqDto {

    private Profile profile;
    private String profileImg;
    private String snsEmail;
    private String provider;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Profile {
        private String nickname;
        private String statusMsg;
    }
}
