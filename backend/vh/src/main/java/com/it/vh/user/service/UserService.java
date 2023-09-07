package com.it.vh.user.service;

import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.exception.NonExistUserIdException;

public interface UserService {
    UserDto getUserProfileByUserId(long userId) throws NonExistUserIdException;

    UserFollowResDto getFollowInfoByUserId(long userId) throws NonExistUserIdException;
}
