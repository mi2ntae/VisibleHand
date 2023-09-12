package com.it.vh.quiz.api.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsQuizRes {
    Long newsQuizId;
    String question;
    String answer;
}
