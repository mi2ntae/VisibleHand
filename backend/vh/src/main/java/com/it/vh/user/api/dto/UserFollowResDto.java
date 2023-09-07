package com.it.vh.user.api.dto;

import com.it.vh.user.domain.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserFollowResDto {
    private int followingCnt;
    private int followerCnt;
}
