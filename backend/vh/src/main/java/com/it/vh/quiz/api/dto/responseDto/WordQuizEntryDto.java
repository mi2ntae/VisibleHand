package com.it.vh.quiz.api.dto.responseDto;

import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.dict.domain.entity.DictionaryType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordQuizEntryDto {
    private Long wordId;
    private String word;
    private String meaning;
    private DictionaryType type;

}