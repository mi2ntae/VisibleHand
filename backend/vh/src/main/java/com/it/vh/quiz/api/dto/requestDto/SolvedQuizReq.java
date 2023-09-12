package com.it.vh.quiz.api.dto.requestDto;

import lombok.Getter;

@Getter
public class SolvedQuizReq {
    long userId;
    Long newsquizId;
    Long wordId;
    boolean correct;
}
