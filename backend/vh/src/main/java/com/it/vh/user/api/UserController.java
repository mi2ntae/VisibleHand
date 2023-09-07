package com.it.vh.user.api;

import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.service.FeedService;
import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.api.dto.UserProfileResDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.exception.NonExistUserIdException;
import com.it.vh.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final FeedService feedService;

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

    @ApiOperation(value = "유저 피드 목록 조회", notes = "유저 피드 목록 조회 5개씩.")
    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<FeedResDto>> getFeedsByUserId(@PathVariable long userId, @RequestParam boolean isMe, @RequestParam int searchType, @RequestParam String keyword, @RequestParam int page) throws NonExistUserIdException {
        return ResponseEntity.ok().body(feedService.getFeedsByUserId(userId, isMe, searchType, keyword, page));
    }
}