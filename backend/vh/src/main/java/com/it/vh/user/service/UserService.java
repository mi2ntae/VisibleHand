package com.it.vh.user.service;

import com.it.vh.user.api.dto.FollowResDto;
import com.it.vh.user.api.dto.NicknameResDto;
import com.it.vh.user.api.dto.UserFollowListResDto;
import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.api.dto.UserProfileReqDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.exception.NonExistUserIdException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    UserDto getUserProfileByUserId(long userId) throws NonExistUserIdException;

    UserFollowResDto getFollowInfoByUserId(long userId) throws NonExistUserIdException;

    Page<UserFollowListResDto> getFollowingListByUserId(long userId, int page);
    Page<UserFollowListResDto> getFollowerListByUserId(long userId, int page);
    Page<UserFollowListResDto> getUserListBykeyword(String keyword, int page);

    void registFollow(FollowResDto followResDto);

    void deleteFollow(FollowResDto followResDto);

    NicknameResDto isDuplicatedNickname(String nickname);
    void createProfile(UserProfileReqDto userProfileReqDto);
    void updateProfile(Long userId, UserProfileReqDto userProfileReqDto);
    void deleteUser(Long userId);

    List<UserFollowListResDto> getRecommendUserListByUserId(long userId);
}
