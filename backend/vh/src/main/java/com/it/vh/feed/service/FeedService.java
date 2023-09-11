package com.it.vh.feed.service;

import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.user.domain.dto.UserDto;

import java.util.List;

public interface FeedService {
    List<FeedResDto> getFeedsByUserId(long userId, int searchType, String keyword, int page);

    List<FeedResDto> getFollowingFeedsByUserId(long userId, int searchType, String keyword, int page);
}
