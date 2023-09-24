package com.it.vh.article.api;

import com.amazonaws.Response;
import com.it.vh.article.api.dto.response.CloudResponseDto;
import com.it.vh.article.service.WordCloudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wordcloud")
@Slf4j
public class WordCloudController {

    private final WordCloudService wordCloudService;

    @GetMapping
    public ResponseEntity<?> getWordCloud(@RequestParam String category, @RequestParam String date) {
        log.info("[WordCloudController getWordCloud] category : {}, date : {}",category,date);
        List<CloudResponseDto> wordCloud = wordCloudService.findCloudByKindAndDate(category, date);
        return ResponseEntity.ok().body(wordCloud);
    }

    @GetMapping("/recent")
    public ResponseEntity<?> recentAndLastDate() {
        return ResponseEntity.ok().body(wordCloudService.recentAndLastDate());
    }
}
