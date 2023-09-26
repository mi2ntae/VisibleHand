package com.it.vh.feed.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedListRes {
    private long userId;
    private String nickname;
    private String profileImg;
    private long feedId;
    private String content;
    private int heart;
    private boolean isHeart;
    private long articleId;
    private String title;
    private LocalDateTime createAt;
    private String kind;
}
