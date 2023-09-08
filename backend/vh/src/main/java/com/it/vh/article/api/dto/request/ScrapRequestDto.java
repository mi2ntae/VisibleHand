package com.it.vh.article.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScrapRequestDto {
    private Long userId;
    private Long articleId;
}
