package com.it.vh.user.service;

import com.it.vh.user.api.dto.*;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.exception.NonExistUserIdException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

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
    void createProfile(MultipartFile file, UserProfileReqDto userProfileReqDto);
    void updateProfile(Long userId, MultipartFile file, UserProfileReqDto userProfileReqDto);
    void deleteUser(Long userId);

    List<UserSearchListDto> getUsersByKeyword(String keyword, long userId, int page);
    List<UserFollowListResDto> getRecommendUserListByUserId(long userId);
}
