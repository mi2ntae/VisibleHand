package com.it.vh.user.service;

import com.it.vh.common.util.S3Uploader;
import com.it.vh.feed.api.dto.FeedRes;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRespository userRespository;
    private final FollowRepository followRepository;
    private final int FOLLOWLIST_PAGE_NUM = 10;
    private final UserRedisService userRedisService;

    private final S3Uploader uploader;

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
                                .userName(follow.getTo().getNickname())
                                .statusMsg(follow.getTo().getStatusMsg())
                                .imageUrl(follow.getTo().getProfileImg())
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
                            .userName(follow.getFrom().getNickname())
                            .statusMsg(follow.getFrom().getStatusMsg())
                            .imageUrl(follow.getFrom().getProfileImg())
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
                                .userName(userList.getNickname())
                                .statusMsg(userList.getStatusMsg())
                                .imageUrl(userList.getProfileImg())
                                .build()
        );
    }

    @Override
    public List<UserFollowListResDto> getUsersByKeyword(String keyword, int page) {

        return userRespository.findUsersByNickname(keyword, PageRequest.of(page, 7));
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

    @Transactional
    @Override
    public void createProfile(MultipartFile file, UserProfileReqDto userProfileReqDto) throws NonExistUserIdException {
        String snsEmail = userProfileReqDto.getSnsEmail();
        log.info("email: {}", snsEmail);
        String provider = userProfileReqDto.getProvider();
        log.info("providerId: {}", provider);

        String s3ImgUrl = null;
        if(!Objects.isNull(file)) {
            log.info("[createProfile]");
            try {
                s3ImgUrl = uploader.upload(file, userProfileReqDto.getSnsEmail());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        log.info(s3ImgUrl);
        Optional<User> findUser = userRespository.findBySnsEmailAndProvider(snsEmail, provider);
        log.info("user: {}", findUser);

        if(!findUser.isPresent())
            throw new NonExistUserIdException();

        User user = User.builder()
                .userId(findUser.get().getUserId())
                .nickname(userProfileReqDto.getProfile().getNickname())
                .statusMsg(userProfileReqDto.getProfile().getStatusMsg())
                .profileImg(s3ImgUrl)
                .snsEmail(findUser.get().getSnsEmail())
                .provider(findUser.get().getProvider())
                .build();
        userRespository.save(user);
    }


    @Transactional
    @Override
    public void updateProfile(Long userId, UserProfileReqDto userProfileReqDto) throws NonExistUserIdException {
        Optional<User> findUser = userRespository.findUserByUserId(userId);
        if(!findUser.isPresent())
            throw new NonExistUserIdException();

        User user = findUser.get();
        user.setNickname(userProfileReqDto.getProfile().getNickname());
        user.setStatusMsg(userProfileReqDto.getProfile().getStatusMsg());
        user.setProfileImg(userProfileReqDto.getProfileImg());
        userRespository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRespository.deleteById(userId);
        userRedisService.deleteRefreshToken(userId.toString());
    }

    @Override
    public List<UserFollowListResDto> getRecommendUserListByUserId(long userId) {
        List<UserFollowListResDto> userList = userRespository.findRecommendUserByUserId(userId);
        return userList;
    }
}
