package com.it.vh.feed.service;

import com.it.vh.feed.api.dto.HeartReq;

public interface HeartService {
    void createHeartByFeedId(HeartReq heartReq);

    void deleteHeartByFeedId(HeartReq heartReq);
}
