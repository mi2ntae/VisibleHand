package com.it.vh.user.api.dto;

import com.it.vh.user.domain.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileRes {
    private String profileImg;
    private String nickname;
    private String statusMsg;

    public static UserProfileRes from(UserDto userDto) {
        return UserProfileRes.builder()
                .profileImg(userDto.getProfileImg())
                .nickname(userDto.getNickname())
                .statusMsg(userDto.getStatusMsg())
                .build();
    }
}
