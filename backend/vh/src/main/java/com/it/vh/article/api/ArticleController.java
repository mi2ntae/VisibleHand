package com.it.vh.article.api;

import com.it.vh.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    @GetMapping
    public ResponseEntity<?> getArticlesBykeyWord (
            @RequestParam String kind,
            @RequestParam String date,
            @RequestParam String word,
            @RequestParam int page
    ) {
        return ResponseEntity.ok().body(articleService.findArticleForSlice(kind,date,word,page));
    }

    //TODO : 기사 상세 조회
    @GetMapping("/{articleId}")
    public ResponseEntity<?> articleDetail() {
        return null;
    }

    //TODO : 기사 스크랩
    @PostMapping("/scrap/{articleId}")
    public ResponseEntity<?> scrap() {
        return null;
    }
}
