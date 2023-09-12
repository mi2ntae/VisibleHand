package com.it.vh.feed.api;

import com.it.vh.feed.api.dto.HeartReq;
import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.exception.NonExistFeedIdException;
import com.it.vh.feed.service.FeedService;
import com.it.vh.feed.service.HeartService;
import com.it.vh.user.exception.NonExistUserIdException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "피드 API", tags = {"Feed"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {
    private final FeedService feedService;
    private final HeartService heartService;

    @ApiOperation(value = "피드 목록 조회", notes = "로그인한 유저에 따른 피드 목록 조회")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FeedResDto>> getFeedsByUserId(@PathVariable long userId, @RequestParam int searchType, @RequestParam(required = false) String keyword, @RequestParam int page) throws NonExistUserIdException {
        List<FeedResDto> userDtos = feedService.getFollowingFeedsByUserId(userId, searchType, keyword, page);
        return ResponseEntity.ok().body(userDtos);
    }

    @ApiOperation(value = "피드 좋아요", notes = "로그인한 유저로 피드 좋아요")
    @PostMapping("/heart")
    public ResponseEntity<Void> createHeartByFeedId(@RequestBody HeartReq heartReq) throws NonExistFeedIdException {
        heartService.createHeartByFeedId(heartReq);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "피드 좋아요 취소", notes = "로그인한 유저로 피드 좋아요 취소")
    @DeleteMapping("/heart")
    public ResponseEntity<Void> deleteHeartByFeedId(@RequestBody HeartReq heartReq) throws NonExistFeedIdException {
        heartService.deleteHeartByFeedId(heartReq);
        return ResponseEntity.ok().build();
    }
}