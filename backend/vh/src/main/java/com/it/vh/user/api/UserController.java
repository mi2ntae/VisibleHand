package com.it.vh.user.api;

import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.service.FeedService;
import com.it.vh.quiz.service.SolvedQuizService;
import com.it.vh.user.api.dto.*;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.exception.NonExistUserIdException;
import com.it.vh.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final FeedService feedService;
    private final SolvedQuizService solvedQuizService;

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
    public ResponseEntity<List<FeedResDto>> getFeedsByUserId(@PathVariable long userId, @RequestParam int searchType, @RequestParam(required = false) String keyword, @RequestParam int page) throws NonExistUserIdException {
        return ResponseEntity.ok().body(feedService.getFeedsByUserId(userId, searchType, keyword, page));
    }

    @ApiOperation(value = "유저 오답 노트 조회", notes = "유저 오답 노트 조회 8개씩.")
    @GetMapping("/reviewnote/{userId}")
    public ResponseEntity<Page<ReviewnoteResDto>> getReviewNotesByUserId(@PathVariable long userId, @RequestParam int page) throws NonExistUserIdException{
        return ResponseEntity.ok().body(solvedQuizService.getReviewNotesByUserId(userId, page));
    }


    @ApiOperation(value = "유저 팔로잉 목록 조회", notes = "팔로잉 목록 조회 10개씩.")
    @GetMapping("/following/{userId}")
    public ResponseEntity<Page<UserFollowListResDto>> getFollowerListByUserId(@PathVariable long userId, @RequestParam int page) throws NonExistUserIdException{
        return ResponseEntity.ok().body(userService.getFollowerListByUserId(userId, page));
    }

    @ApiOperation(value = "유저 목록 검색", notes = "검색한 유저 목록을 조회할수 있고 10개씩 조회가 되도록 진행한다.")
    @GetMapping("/")
    public ResponseEntity<Page<UserFollowListResDto>> getUserListByKeyWord(@RequestParam String keyword, @RequestParam int page){
        return ResponseEntity.ok().body(userService.getUserListBykeyword(keyword,page));
    }

    @ApiOperation(value = "다른 유저 팔로우", notes ="사용자가 타인의 계정을 팔로우합니다")
    @PostMapping("/follow")
    public ResponseEntity<Void> followUser(@RequestBody @Valid FollowResDto followResDto){
        userService.registFollow(followResDto);
        return ResponseEntity.ok().build();
    }
}