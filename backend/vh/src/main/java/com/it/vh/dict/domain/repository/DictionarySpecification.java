package com.it.vh.dict.domain.repository;

import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.dict.domain.entity.DictionaryType;
import org.springframework.data.jpa.domain.Specification;

public class DictionarySpecification {

    public static Specification<Dictionary> equalType(DictionaryType type) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type));
    }

    public static Specification<Dictionary> likeKeyword(String keyword) {
        String pattern = "%" + keyword + "%";
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("word"), pattern));
    }
}
