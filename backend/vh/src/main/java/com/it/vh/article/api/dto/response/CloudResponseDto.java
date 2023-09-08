package com.it.vh.article.api.dto.response;

import com.it.vh.article.domain.entity.ArticleKind;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CloudResponseDto {
    private Long wordId;
    private String word;
    private Integer count;
    private LocalDateTime issueDate;
    private ArticleKind kind;
}
