package com.it.vh.user.api;

import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.api.dto.UserProfileResDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.exception.NonExistUserIdException;
import com.it.vh.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "유저 프로필 조회", notes = "유저 프로필 조회")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResDto> getUserProfileByUserId(@PathVariable long userId) throws NonExistUserIdException {
        UserDto userDto = userService.getUserProfileByUserId(userId);
        return ResponseEntity.ok().body(UserProfileResDto.from(userDto));
    }

    @ApiOperation(value = "유저 팔로우 정보 조회", notes = "유저 팔로우 정보 조회 (팔로잉 수, 팔로워 수)")
    @GetMapping("/follow/{userId}")
    public ResponseEntity<UserFollowResDto> getFollowInfoByUserId(@PathVariable long userId) throws NonExistUserIdException {
        return ResponseEntity.ok().body(userService.getFollowInfoByUserId(userId));
    }
}