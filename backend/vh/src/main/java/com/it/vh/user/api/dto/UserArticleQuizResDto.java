package com.it.vh.user.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserArticleQuizResDto {
    private int financeCnt;
    private int stockCnt;
    private int industryCnt;
    private int ventureCnt;
    private int realEstateCnt;
    private int globalCnt;
    private int livingCnt;
    private int generalCnt;
}
