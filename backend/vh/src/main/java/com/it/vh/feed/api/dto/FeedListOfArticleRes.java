package com.it.vh.feed.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedListOfArticleRes {
    private long feedId;
    private String profileImg;
    private String nickname;
    private String content;
    private int heart;
    private int isHeart;
    private LocalDateTime createdAt;
}
