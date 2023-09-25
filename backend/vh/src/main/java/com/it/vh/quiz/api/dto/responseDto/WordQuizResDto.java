package com.it.vh.quiz.api.dto.responseDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordQuizResDto {

    private Boolean allSolved;
    private List<WordQuizEntryDto> entries;
}
