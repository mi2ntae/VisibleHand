package com.it.vh.dict.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DictionaryType {
    //경제 사회 과학 경영 공공 금융
    경제("ECONOMY"),
    사회("SOCIAL"),
    과학("SCIENCE"),
    경영("MANAGEMENT"),
    공공("PUBLIC"),
    금융("FINANCE");

    private final String type;
}
