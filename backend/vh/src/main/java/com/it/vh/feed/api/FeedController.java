package com.it.vh.feed.api;

import com.it.vh.feed.api.dto.*;
import com.it.vh.feed.exception.NonExistFeedIdException;
import com.it.vh.feed.service.FeedService;
import com.it.vh.feed.service.HeartService;
import com.it.vh.user.exception.NonExistUserIdException;
import com.it.vh.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private final UserService userService;

    @ApiOperation(value = "피드 목록 조회", notes = "로그인한 유저에 따른 피드 목록 조회")
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<?>> getFeedsByUserId(@PathVariable long userId, @RequestParam int searchType, @RequestParam(required = false) String keyword, @RequestParam int page) throws NonExistUserIdException {
        List<?> res;
        switch(searchType) {
            case 0:
                res = feedService.searchFeedsByUserId(userId, searchType, keyword, page);
                break;
            case 1:
                res = userService.getUsersByKeyword("%" + keyword + "%", page);
                break;
            default:
                res = feedService.searchFeedsByUserId(userId, searchType, keyword, page);
        }
        return ResponseEntity.ok().body(res);
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

    @ApiOperation(value = "뉴스별 피드 목록", notes = "해당 뉴스에 대한 피드 목록 조회")
    @GetMapping("/list/{articleId}/{userId}")
    public ResponseEntity<List<FeedListOfArticleRes>> getFeedsByArticleId(@PathVariable long articleId, @PathVariable long userId, @RequestParam int page) {
        List<FeedListOfArticleRes> res = feedService.getFeedsByArticleId(articleId, userId, page);
        return ResponseEntity.ok().body(res);
    }


    @ApiOperation(value = "뉴스 피드 등록", notes = "로그인한 유저의 계정으로 해당 뉴스에 대한 피드 등록")
    @PostMapping("")
    public ResponseEntity<Long> writeFeed(@RequestBody FeedReq feedReq) {
        long feedId = feedService.registFeed(feedReq.getUserId(), feedReq.getArticleId(), feedReq.getContent(), feedReq.isShared());
        return ResponseEntity.ok().body(feedId);
    }

    @ApiOperation(value = "뉴스에 대한 내 피드 조회", notes = "해당 뉴스에 대한 내 피드 조회")
    @GetMapping("/{articleId}/{userId}")
    public ResponseEntity<MyFeedRes> getFeedByArticleIdAndUserId(@PathVariable long articleId, @PathVariable long userId) {
        MyFeedRes res = feedService.getFeedByArticleIdAndUserId(articleId, userId);
        return ResponseEntity.ok().body(res);
    }

    @ApiOperation(value = "뉴스 피드 수정")
    @PutMapping("/{feedId}")
    public ResponseEntity<Void> modifyFeedByFeedId(@PathVariable long feedId, @RequestBody FeedUpdateReq feedUpdateReq) {
        feedService.updateFeed(feedId, feedUpdateReq.getContent(), feedUpdateReq.isShared());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "뉴스 피드 삭제")
    @DeleteMapping("/{feedId}")
    public ResponseEntity<Void> deleteFeedByFeedId(@PathVariable long feedId) {
        feedService.deleteFeed(feedId);
        return ResponseEntity.ok().build();
    }
}