package com.it.vh.article.service;

import com.it.vh.article.api.dto.response.CloudResponseDto;
import com.it.vh.article.domain.entity.Cloud;
import com.it.vh.article.domain.repository.WordCloudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
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
}
