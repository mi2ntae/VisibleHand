package com.it.vh.feed.service;

import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.api.dto.HeartCreateReq;

import java.util.List;

public interface HeartService {
    void createHeartByFeedId(HeartCreateReq feedHeartCreateReq);
}
