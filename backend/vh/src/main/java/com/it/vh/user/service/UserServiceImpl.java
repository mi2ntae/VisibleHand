package com.it.vh.user.service;

import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.FollowRepository;
import com.it.vh.user.domain.repository.UserRepository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRespository;
    private final FollowRepository followRepository;

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
}
