package com.it.vh.dict.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DictionaryType {
    //경제 사회 과학 경영 공공 금융
    ENCONOMY("경제"),
    SOCIAL("사회"),
    SCIENCE("과학"),
    MANAGEMENT("경영"),
    PUBLIC("공공"),
    FINANCE("금융");

    private final String type;
}
