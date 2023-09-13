package com.it.vh.article.api;

import com.it.vh.article.api.dto.response.CloudResponseDto;
import com.it.vh.article.service.WordCloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wordcloud")
public class WordCloudController {

    private final WordCloudService wordCloudService;

    @GetMapping
    public ResponseEntity<?> getWordCloud(@RequestParam String category, @RequestParam String date) {
        List<CloudResponseDto> wordCloud = wordCloudService.findCloudByKindAndDate(category, date);
        return ResponseEntity.ok().body(wordCloud);
    }
}
