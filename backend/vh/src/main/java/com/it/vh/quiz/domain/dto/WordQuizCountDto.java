package com.it.vh.quiz.domain.dto;

import com.it.vh.dict.domain.entity.DictionaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class WordQuizCountDto {
    private DictionaryType type;
    private long cnt;
}
