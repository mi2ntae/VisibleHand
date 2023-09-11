package com.it.vh.dict.api.dto.responseDto;

import com.it.vh.dict.domain.entity.DictionaryType;
import lombok.*;
import org.checkerframework.checker.units.qual.N;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryResponseDto {
    private Long wordId;
    private String word;
    private String meaning;
    private DictionaryType type;
}

