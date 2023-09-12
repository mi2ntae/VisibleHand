package com.it.vh.feed.service;

import com.it.vh.feed.api.dto.FeedListOfArticleResDto;
import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.api.dto.MyFeedRes;

import java.util.List;

public interface FeedService {
    List<FeedResDto> getFeedsByUserId(long userId, int searchType, String keyword, int page);

    List<FeedResDto> getFollowingFeedsByUserId(long userId, int searchType, String keyword, int page);

    List<FeedListOfArticleResDto> getFeedsByArticleId(long articleId, long userId, int page);

    void registFeed(long userId, long articleId, String content, boolean isShared);

    MyFeedRes getFeedByArticleIdAndUserId(long articleId, long userId);

    void updateFeed(long feedId, String content, boolean isShared);

    void deleteFeed(long feedId);
}
