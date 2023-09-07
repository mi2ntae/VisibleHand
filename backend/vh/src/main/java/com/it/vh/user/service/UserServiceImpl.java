package com.it.vh.user.service;

import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRespository repo;
    @Override
    public UserDto getUserProfileByUserId(long userId) throws NonExistUserIdException{
        Optional<User> optionalUser = repo.findUserByUserId(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        return UserDto.from(optionalUser.get());
    }
}
