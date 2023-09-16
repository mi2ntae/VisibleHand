package com.it.vh.feed.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedUpdateReq {
    private String content;
    private boolean isShared;
}
