package com.it.vh.user.api.dto;

import com.it.vh.quiz.domain.dto.UserArticleQuizResDto;
import com.it.vh.quiz.domain.dto.UserWordQuizResDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQuizStatusResDto {
    private UserWordQuizResDto word;
    private UserArticleQuizResDto article;

    public static UserQuizStatusResDto of(UserWordQuizResDto userWordQuizResDto, UserArticleQuizResDto userArticleQuizResDto) {
        return UserQuizStatusResDto.builder()
                .word(userWordQuizResDto)
                .article(userArticleQuizResDto).build();
    }
}
