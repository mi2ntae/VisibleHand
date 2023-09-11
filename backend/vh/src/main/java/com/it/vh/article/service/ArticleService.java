package com.it.vh.article.service;

import com.it.vh.article.api.dto.response.ArticleResponseDto;
import com.it.vh.article.domain.entity.Article;
import com.it.vh.article.domain.entity.Scrap;
import com.it.vh.article.domain.repository.ArticleRepository;
import com.it.vh.article.domain.repository.ScrapRepository;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ScrapRepository scrapRepository;
    private final UserRespository userRespository;


    public Slice<ArticleResponseDto> findArticleForSlice(String kind, String date, String word, int page) {
        return articleRepository.findSliceByConditions(kind, date, word, PageRequest.of(page, 10))
                .map(this::convertArticleEntityToDto);
    }

    public ArticleResponseDto getArticleDetail(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if(ObjectUtils.isEmpty(article)) return null;
        return convertArticleEntityToDto(article);
    }

    public Boolean isArticleScraped(Long articleId, Long userId) {
        Scrap scrap = scrapRepository.findByArticle_ArticleIdAndUser_UserId(articleId, userId).orElse(null);
        if(Objects.isNull(scrap)) return false;
        return true;
    }
    @Transactional
    public Boolean scrap(Long articleId, Long userId) {
        if(!isArticleScraped(articleId, userId)) {
            Article article = articleRepository.findById(articleId).orElse(null);
            User user = userRespository.findById(userId).orElse(null);
            if(Objects.isNull(article) || Objects.isNull(user)) return false;
            scrapRepository.save(Scrap.builder()
                    .article(article)
                    .user(user).build());
        }
        return true;
    }

    public ArticleResponseDto convertArticleEntityToDto(Article article) {
        return ArticleResponseDto.builder()
                .articleId(article.getArticleId())
                .issueDate(article.getIssueDate())
                .title(article.getTitle())
                .url(article.getUrl())
                .company(article.getCompany())
                .content(article.getContent())
                .editor(article.getEditor())
                .kind(article.getKind())
                .summary(article.getSummary())
                .thumbnail(article.getThumbnail()).build();
    }
}
