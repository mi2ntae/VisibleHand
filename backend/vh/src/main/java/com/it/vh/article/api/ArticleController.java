package com.it.vh.article.api;

import com.it.vh.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

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
}
