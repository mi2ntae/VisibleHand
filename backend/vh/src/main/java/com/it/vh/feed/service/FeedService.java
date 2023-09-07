package com.it.vh.feed.service;

import com.it.vh.feed.api.dto.FeedResDto;

import java.util.List;

public interface FeedService {
    List<FeedResDto> getFeedsByUserId(long userId, boolean isMe, int searchType, String keyword, int page);
}
