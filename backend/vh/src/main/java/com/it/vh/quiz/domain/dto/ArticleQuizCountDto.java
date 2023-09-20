package com.it.vh.quiz.domain.dto;

import com.it.vh.article.domain.entity.ArticleKind;
import com.it.vh.dict.domain.entity.DictionaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ArticleQuizCountDto {
    private ArticleKind type;
    private long cnt;
}
