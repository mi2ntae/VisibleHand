package com.it.vh.article.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapListRes {
    private long scrapId;
    private long articleId;
    private String thumbnail;
    private String title;
}
