package com.it.vh.user.api;

import com.it.vh.feed.api.dto.FeedRes;
import com.it.vh.feed.service.FeedService;
import com.it.vh.quiz.service.SolvedQuizService;
import com.it.vh.user.api.dto.*;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.exception.NonExistUserIdException;
import com.it.vh.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<FeedRes>> getFeedsByUserId(@PathVariable long userId, @RequestParam int searchType, @RequestParam(required = false) String keyword, @RequestParam int page) throws NonExistUserIdException {
        return ResponseEntity.ok().body(feedService.getFeedsByUserId(userId, searchType, keyword, page));
    }

    @ApiOperation(value = "유저 오답 노트 조회", notes = "유저 오답 노트 조회 8개씩.")
    @GetMapping("/reviewnote/{userId}")
    public ResponseEntity<Page<ReviewnoteResDto>> getReviewNotesByUserId(@PathVariable long userId, @RequestParam int page) throws NonExistUserIdException{
        return ResponseEntity.ok().body(solvedQuizService.getReviewNotesByUserId(userId, page));
    }

    @ApiOperation(value = "유저 스트릭 조회", notes = "유저의 날짜별 풀이 가중치 조회")
    @GetMapping("/streak/{userId}")
    public ResponseEntity<List<StreakResDto>> getUserStreak(@PathVariable long userId) throws NonExistUserIdException{
        return ResponseEntity.ok().body(solvedQuizService.getUserStreak(userId));
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

    @ApiOperation(value = "팔로우 취소", notes="팔로우를 취소합니다.")
    @DeleteMapping("/follow")
    public ResponseEntity<Void>  deletefollow(@RequestBody @Valid FollowResDto followResDto){
        userService.deleteFollow(followResDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "유저 닉네임 중복 검사", notes = "유저 닉네임 중복 여부를 검사합니다.")
    @GetMapping("/auth/nickname")
    public ResponseEntity<NicknameResDto> isDuplicatedNickname(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok().body(userService.isDuplicatedNickname(nickname));
    }

    @ApiOperation(value = "유저 프로필 등록", notes = "유저 프로필을 등록합니다.")
    @PostMapping("/profile")
    public ResponseEntity<?> createProfile(@RequestBody UserProfileReqDto userProfileReqDto) {
        userService.createProfile(userProfileReqDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "유저 프로필 수정", notes = "유저 프로필을 수정합니다.")
    @PutMapping("/profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId, @RequestBody UserProfileReqDto userProfileReqDto) {
        userService.updateProfile(userId, userProfileReqDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "유저 탈퇴", notes = "유저가 탈퇴합니다.")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "추천 사용자 조회", notes = "로그인한 유저가 팔로우하는 유저들이 가장 많이 팔로우하는 유저 조회")
    @GetMapping("/recommend/{userId}")
    public ResponseEntity<List<UserFollowListResDto>> getRecommendUserByUserId(@PathVariable long userId) {
        List<UserFollowListResDto> res = userService.getRecommendUserListByUserId(userId);
        return ResponseEntity.ok().body(res);
    }
}