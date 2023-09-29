package com.it.vh.quiz.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserArticleQuizResDto {
    private long[] cnts;

    public static UserArticleQuizResDto from(List<ArticleQuizCountDto> articleQuizCountDtoList) {
        long[] cnts = new long[8];
        for(ArticleQuizCountDto articleQuizCountDto: articleQuizCountDtoList) cnts[articleQuizCountDto.getType().ordinal()] = articleQuizCountDto.getCnt();
        return UserArticleQuizResDto.builder().cnts(cnts).build();
    }
}
