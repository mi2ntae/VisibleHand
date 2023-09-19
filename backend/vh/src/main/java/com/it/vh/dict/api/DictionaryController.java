package com.it.vh.dict.api;

import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.dict.domain.entity.DictionaryType;
import com.it.vh.dict.domain.repository.DictionarySpecification;
import com.it.vh.dict.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dict")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping
    public ResponseEntity<?> getWords (
            @RequestParam(required = false) DictionaryType type,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = true) int page
    ) {
        Specification<Dictionary> spec = ((root, query, criteriaBuilder) -> null);
        if(!ObjectUtils.isEmpty(keyword)) spec = spec.and(DictionarySpecification.likeKeyword(keyword));
        if(!ObjectUtils.isEmpty(type)) spec = spec.and(DictionarySpecification.equalType(type));
        return ResponseEntity.ok().body(dictionaryService.getwords(page, spec));
    }

}
