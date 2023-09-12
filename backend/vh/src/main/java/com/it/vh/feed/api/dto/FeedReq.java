package com.it.vh.feed.api.dto;

import com.it.vh.article.domain.entity.Article;
import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedReq {
    private long userId;
    private long articleId;
    private String content;
    private boolean isShared;
}
