package com.it.vh.article.api;

import com.it.vh.article.api.dto.ScrapListRes;
import com.it.vh.article.domain.exception.NonExistScrapIdException;
import com.it.vh.article.service.ScrapService;
import com.it.vh.common.exception.AuthenticationAccessForbiddenException;
import com.it.vh.user.exception.NonExistUserIdException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "기사 API", tags = {"Article"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    private final ScrapService scrapService;

    @ApiOperation(value = "기사 스크랩 목록 조회", notes = "해당 유저의 기사 스크랩 목록 조회")
    @GetMapping("/scrap/{userId}")
    public ResponseEntity<Page<ScrapListRes>> getScrapListByUserId(@PathVariable long userId, @RequestParam(required = false) String keyword, @RequestParam int page) throws AuthenticationAccessForbiddenException, NonExistUserIdException {
        return ResponseEntity.ok().body(scrapService.getScrapListByUserId(userId, keyword, page));
    }

    @ApiOperation(value = "기사 스크랩 삭제", notes = "해당 유저의 기사 스크랩 삭제")
    @DeleteMapping("/scrap/{scrapId}")
    public ResponseEntity<Void> deleteScrapByScrapId(@PathVariable long scrapId) throws NonExistScrapIdException {
        scrapService.deleteScrapByScrapId(scrapId);
        return ResponseEntity.ok().build();
    }
}