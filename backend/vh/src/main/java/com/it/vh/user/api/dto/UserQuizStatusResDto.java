package com.it.vh.user.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQuizStatusResDto {
    private UserWordQuizResDto word;
    private UserArticleQuizResDto article;
}
