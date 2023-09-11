package com.it.vh.feed.api;

import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.service.FeedService;
import com.it.vh.quiz.service.SolvedQuizService;
import com.it.vh.user.api.dto.ReviewnoteResDto;
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

@Api(value = "피드 API", tags = {"Feed"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {
    private final FeedService feedService;

    @ApiOperation(value = "피드 목록 조회", notes = "로그인한 유저에 따른 피드 목록 조회")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FeedResDto>> getFeedsByUserId(@PathVariable long userId, @RequestParam int searchType, @RequestParam(required = false) String keyword, @RequestParam int page) throws NonExistUserIdException {
        List<FeedResDto> userDtos = feedService.getFollowingFeedsByUserId(userId, searchType, keyword, page);
        return ResponseEntity.ok().body(userDtos);
    }
}