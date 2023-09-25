package com.it.vh.dict.service;

import com.it.vh.dict.api.dto.responseDto.DictionaryResponseDto;
import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.dict.domain.repository.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public Page<DictionaryResponseDto> getwords(int page, Specification<Dictionary> spec) {
        return dictionaryRepository.findAll(spec, PageRequest.of(page,10)).map(this::CovertDictionaryToDto);
    }

    public DictionaryResponseDto CovertDictionaryToDto(Dictionary dictionary) {
        return DictionaryResponseDto.builder()
                .wordId(dictionary.getWordId())
                .word(dictionary.getWord())
                .meaning(dictionary.getMeaning())
                .type(dictionary.getType()).build();
    }

}
