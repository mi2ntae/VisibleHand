package com.it.vh.feed.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HeartCreateReq {
    private long feedId;
}
