package com.it.vh.article.api.dto.response;

import com.it.vh.article.domain.entity.ArticleKind;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ArticleResponseDto {
    private Long articleId;
    private String title;
    private String content;
    private String thumbnail;
    private LocalDateTime issueDate;
    private String company;
    private String editor;
    private ArticleKind kind;
    private String url;
    private String summary;
}
