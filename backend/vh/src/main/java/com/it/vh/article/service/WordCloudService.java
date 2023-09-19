package com.it.vh.article.service;

import com.it.vh.article.api.dto.response.CloudResponseDto;
import com.it.vh.article.domain.entity.Cloud;
import com.it.vh.article.domain.repository.WordCloudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordCloudService {

    private final WordCloudRepository cloudRepository;

    public List<CloudResponseDto> findCloudByKindAndDate(String kind, String date) {
        List<Cloud> wordCloud = cloudRepository.findByArticleKindAndIssueDate(date, kind).orElse(null);
        if(CollectionUtils.isEmpty(wordCloud)) return null;
        return wordCloud.stream().map(cloud -> CloudResponseDto.builder()
                .wordId(cloud.getWordId())
                .word(cloud.getWord())
                .count(cloud.getCount())
                .kind(cloud.getKind())
                .issueDate(cloud.getIssueDate()).build())
                .collect(Collectors.toList());
    }

    public Map<String, LocalDateTime> recentAndLastDate() {
        Cloud recent = cloudRepository.findTopByOrderByIssueDateDesc().orElse(null);
        Cloud last = cloudRepository.findTopByOrderByIssueDateAsc().orElse(null);
        if(Objects.isNull(recent) || Objects.isNull(last) ) return null;
        HashMap<String, LocalDateTime> map = new HashMap<>();
        map.put("recent",recent.getIssueDate());
        map.put("last",last.getIssueDate());
        return map;
    }
}
