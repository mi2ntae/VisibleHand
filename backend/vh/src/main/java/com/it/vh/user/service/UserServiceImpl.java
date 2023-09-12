package com.it.vh.user.service;

import com.it.vh.user.api.dto.FollowResDto;
import com.it.vh.user.api.dto.NicknameResDto;
import com.it.vh.user.api.dto.UserFollowListResDto;
import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.api.dto.UserProfileReqDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.domain.entity.Follow;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.FollowRepository;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRespository userRespository;
    private final FollowRepository followRepository;
    private final int FOLLOWLIST_PAGE_NUM = 10;
    @Override
    public UserDto getUserProfileByUserId(long userId) throws NonExistUserIdException{
        Optional<User> optionalUser = userRespository.findUserByUserId(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        return UserDto.from(optionalUser.get());
    }

    @Override
    public UserFollowResDto getFollowInfoByUserId(long userId) throws NonExistUserIdException {
        Optional<User> optionalUser = userRespository.findUserByUserId(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        return UserFollowResDto.builder()
                .followingCnt(followRepository.countFollowsByFrom_UserId(userId))
                .followerCnt(followRepository.countFollowsByTo_UserId(userId))
                .build();
    }

    @Override
    public Page<UserFollowListResDto> getFollowingListByUserId(long userId, int page) {
        log.info("내가 팔로잉하고 있는 유저 목록을 조회하는 Service입니다.");
        return followRepository.findFollowsByFrom_UserId(userId, PageRequest.of(page, FOLLOWLIST_PAGE_NUM)).map(
                follow ->
                        UserFollowListResDto.builder()
                                .userId(follow.getTo().getUserId())
                                .UserName(follow.getTo().getNickname())
                                .statusMsg(follow.getTo().getStatusMsg())
                                .iamgeUrl(follow.getTo().getProfileImg())
                                .build()
        );
    }

    @Override
    public Page<UserFollowListResDto> getFollowerListByUserId(long userId, int page) {
        log.info("나를 팔로잉하고 있는 유저 목록을 조회하는 Service입니다.");

//        Page<UserFollowListResDto> userList = new ArrayList<>();
        return followRepository.findFollowsByTo_UserId(userId, PageRequest.of(page, FOLLOWLIST_PAGE_NUM)).map(
                follow ->
                    UserFollowListResDto.builder()
                            .userId(follow.getFrom().getUserId())
                            .UserName(follow.getFrom().getNickname())
                            .statusMsg(follow.getFrom().getStatusMsg())
                            .iamgeUrl(follow.getFrom().getProfileImg())
                            .build()
        );

    }

    @Override
    public Page<UserFollowListResDto> getUserListBykeyword(String keyword, int page) {
        log.info(keyword+"를 기반으로 유사한 사용자들을 검색합니다.");
        return userRespository.findUsersByNicknameContains(keyword,PageRequest.of(page,FOLLOWLIST_PAGE_NUM)).map(
                userList ->
                        UserFollowListResDto.builder()
                                .userId(userList.getUserId())
                                .UserName(userList.getNickname())
                                .statusMsg(userList.getStatusMsg())
                                .iamgeUrl(userList.getProfileImg())
                                .build()
        );
    }

    @Override
    public void registFollow(FollowResDto followResDto) {
        User from = userRespository.findUserByUserId(followResDto.getFromId()).get();
        User to = userRespository.findUserByUserId(followResDto.getToId()).get();
        followRepository.save(Follow.builder()
                        .to(to)
                        .from(from)
                                .build());
    }

    @Override
    public void deleteFollow(FollowResDto followResDto) {
        User from = userRespository.findUserByUserId(followResDto.getFromId()).get();
        User to = userRespository.findUserByUserId(followResDto.getToId()).get();
        followRepository.deleteByFromAndTo(from,to);
    }

    @Override
    public NicknameResDto isDuplicatedNickname(String nickname) {
        Optional<User> findUser = userRespository.findByNickname(nickname);
        if (findUser.isPresent()) {
            return NicknameResDto.builder().isDuplicated(1).build();
        } else {
            return NicknameResDto.builder().isDuplicated(0).build();
        }
    }

    @Override
    public void createProfile(UserProfileReqDto userProfileReqDto) {
        User user = User.builder()
            .nickname(userProfileReqDto.getProfile().getNickname())
            .statusMsg(userProfileReqDto.getProfile().getStatusMsg())
            .profileImg(userProfileReqDto.getProfileImg())
            .snsEmail(userProfileReqDto.getSnsEmail())
            .build();
        userRespository.save(user);
    }

    @Override
    public void updateProfile(Long userId, UserProfileReqDto userProfileReqDto) {
        Optional<User> findUser = userRespository.findUserByUserId(userId);
        if(findUser.isPresent()) {
            User user = findUser.get();
            user.setNickname(userProfileReqDto.getProfile().getNickname());
            user.setStatusMsg(userProfileReqDto.getProfile().getStatusMsg());
            user.setProfileImg(userProfileReqDto.getProfileImg());
            userRespository.save(user);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRespository.deleteById(userId);
    }
}
