package com.it.vh.quiz.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWordQuizResDto {
    private long[] cnts;

    public static UserWordQuizResDto from (List<WordQuizCountDto> wordQuizCountDtoList) {
        long[] cnts = new long[7];
        for(WordQuizCountDto wordQuizCountDto : wordQuizCountDtoList)  cnts[wordQuizCountDto.getType().ordinal()] = wordQuizCountDto.getCnt();
        return UserWordQuizResDto.builder().cnts(cnts).build();
    }
}
