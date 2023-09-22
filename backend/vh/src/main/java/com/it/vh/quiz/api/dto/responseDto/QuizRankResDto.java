package com.it.vh.quiz.api.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizRankResDto {
    private long userId;
    private String nickname;
    private String profileImg;
    private String statusMsg;
    private long solved;
}
