package com.it.vh.feed.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedRes {
    private long feedId;
    private String content;
    private int heart;
    private int isHeart;
    private long articleId;
    private String title;
    private LocalDateTime createAt;
}
