package com.it.vh.user.service;

import com.it.vh.user.api.dto.UserFollowListResDto;
import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.domain.entity.Follow;
import com.it.vh.user.exception.NonExistUserIdException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserDto getUserProfileByUserId(long userId) throws NonExistUserIdException;

    UserFollowResDto getFollowInfoByUserId(long userId) throws NonExistUserIdException;

    Page<UserFollowListResDto> getFollowingListByUserId(long userId, int page);
    Page<UserFollowListResDto> getFollowerListByUserId(long userId, int page);
}
