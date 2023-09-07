package com.it.vh.feed.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedResDto {
    private int feedId;
    private String content;
    private int heart;
    private int isHeart;
    private long articleId;
    private String title;
    private LocalDateTime createAt;
}
