package com.it.vh.article.service;

import com.it.vh.article.api.dto.response.ArticlePageResponseDto;
import com.it.vh.article.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Slice<ArticlePageResponseDto> findArticleForSlice(String kind, String date, String word, int page) {
        return articleRepository.findSliceByConditions(kind, date, word, PageRequest.of(page, 10))
                .map(article -> ArticlePageResponseDto.builder()
                        .articleId(article.getArticleId())
                        .issueDate(article.getIssueDate())
                        .title(article.getTitle())
                        .url(article.getUrl())
                        .company(article.getCompany())
                        .content(article.getContent())
                        .editor(article.getEditor())
                        .kind(article.getKind())
                        .summary(article.getSummary())
                        .thumbnail(article.getThumbnail()).build());
    }
}
