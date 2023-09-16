package com.it.vh.feed.service;

import com.it.vh.feed.api.dto.FeedListOfArticleRes;
import com.it.vh.feed.api.dto.FeedListRes;
import com.it.vh.feed.api.dto.FeedRes;
import com.it.vh.feed.api.dto.MyFeedRes;

import java.util.List;

public interface FeedService {
    List<FeedRes> getFeedsByUserId(long userId, int searchType, String keyword, int page);

    List<FeedListRes> searchFeedsByUserId(long userId, int searchType, String keyword, int page);

    List<FeedListOfArticleRes> getFeedsByArticleId(long articleId, long userId, int page);

    void registFeed(long userId, long articleId, String content, boolean isShared);

    MyFeedRes getFeedByArticleIdAndUserId(long articleId, long userId);

    void updateFeed(long feedId, String content, boolean isShared);

    void deleteFeed(long feedId);
}
