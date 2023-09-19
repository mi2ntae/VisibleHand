package com.it.vh.article.api;


import com.it.vh.article.api.dto.ScrapListResDto;
import com.it.vh.article.domain.exception.NonExistScrapIdException;
import com.it.vh.article.service.ScrapService;
import com.it.vh.common.exception.AuthenticationAccessForbiddenException;
import com.it.vh.article.api.dto.response.RankRes;
import com.it.vh.user.exception.NonExistUserIdException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.it.vh.article.service.ArticleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "기사 API", tags = {"Article"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    private final ScrapService scrapService;

    private final ArticleService articleService;

    //TODO : 예외처리 (MethodArgumentTypeMismatchException / MissingServletRequestParameterException)
    @GetMapping
    public ResponseEntity<?> getArticlesByKeyword (
            @RequestParam String kind,
            @RequestParam String date,
            @RequestParam String word,
            @RequestParam int page
    ) {
        return ResponseEntity.ok().body(articleService.findArticleForSlice(kind,date,word,page));
    }

    //TODO : 로그인기능 개발 후 Handler 적용 ->  user 검증 방법 (로그인 유저, 요청한 정보의 유저가 같은지/ 존재하는 유저인지 등)
    @GetMapping("/{articleId}/{userId}")
    public ResponseEntity<?> articleDetail(@PathVariable Long articleId, @PathVariable Long userId) {
        Map<String, Object> res = new HashMap<>();
        res.put("article", articleService.getArticleDetail(articleId));
        res.put("scraped", articleService.isArticleScraped(articleId, userId));
        return ResponseEntity.ok().body(res);
    }


    //TODO : 로그인기능 개발 후 Handler 적용 ->  user 검증 방법 (로그인 유저, 요청한 정보의 유저가 같은지/ 존재하는 유저인지 등)
    @PostMapping("/scrap/{articleId}/{userId}")
    public ResponseEntity<?> scrap(@PathVariable Long articleId, @PathVariable Long userId) {
        if(articleService.scrap(articleId, userId)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "기사 스크랩 목록 조회", notes = "해당 유저의 기사 스크랩 목록 조회 8개씩")
    @GetMapping("/scrap/{userId}")
    public ResponseEntity<Page<ScrapListResDto>> getScrapListByUserId(@PathVariable long userId, @RequestParam(required = false) String keyword, @RequestParam int page) throws AuthenticationAccessForbiddenException, NonExistUserIdException {
        return ResponseEntity.ok().body(scrapService.getScrapListByUserId(userId, keyword, page));
    }

    @ApiOperation(value = "기사 스크랩 삭제", notes = "해당 유저의 기사 스크랩 삭제")
    @DeleteMapping("/scrap/{scrapId}")
    public ResponseEntity<Void> deleteScrapByScrapId(@PathVariable long scrapId) throws NonExistScrapIdException {
        scrapService.deleteScrapByScrapId(scrapId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "피드에 많이 언급된 뉴스 조회", notes = "전체 사용자가 많이 언급한 뉴스를 5위까지 조회")
    @GetMapping("/rank")
    public ResponseEntity<List<RankRes>> getMostMentionedArticles() {
        List<RankRes> res = articleService.getArticleRanking();
        return ResponseEntity.ok().body(res);
    }
}

