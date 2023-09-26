package com.it.vh.quiz.service;

import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.dict.domain.repository.DictionaryRepository;
import com.it.vh.quiz.api.dto.responseDto.NewsQuizRes;
import com.it.vh.quiz.api.dto.responseDto.QuizRankResDto;
import com.it.vh.quiz.api.dto.responseDto.WordQuizEntryDto;
import com.it.vh.quiz.api.dto.responseDto.WordQuizResDto;
import com.it.vh.quiz.domain.entity.NewsQuiz;
import com.it.vh.quiz.domain.exception.NonExistNewsQuizException;
import com.it.vh.quiz.domain.repository.NewsQuizRepository;
import com.it.vh.quiz.domain.repository.SolvedQuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class QuizServiceImpl implements QuizService{
    private final NewsQuizRepository newsQuizRepository;
    private final SolvedQuizRepository solvedQuizRepository;
    private final DictionaryRepository dictionaryRepository;
    private final int rankTop = 5;
    private final static int limit = 1;

    @Override
    public NewsQuizRes findByArticleId(Long articleId) throws NonExistNewsQuizException {
        Optional<NewsQuiz> nq=newsQuizRepository.findByArticle_ArticleId(articleId);
        NewsQuizRes nqr=new NewsQuizRes();
        if(nq.isEmpty()) {
            nqr.setNewsQuizId(Long.valueOf(-1));
            return nqr;
        }
        nqr.setQuestion(nq.get().getQuestion());
        nqr.setAnswer(nq.get().getAnswer());
        nqr.setNewsQuizId(nq.get().getNewsquizId());
        return nqr;
    }

    @Override
    public List<QuizRankResDto> getQuizRank() {
        LocalDate now = LocalDate.now();
        int day = now.get(ChronoField.DAY_OF_WEEK);
        if(day == 7) day = 0;
        LocalDateTime start = now.minusDays(day).atStartOfDay();
        LocalDateTime end = start.plusDays(7).minusSeconds(1);
        Page<QuizRankResDto> page = solvedQuizRepository.getQuizRank(start, end, PageRequest.of(0, rankTop));
        return page.getContent();
    }

    @Override
    public WordQuizResDto randomQuizs(Long userId) {
        //userId 검증 안함
        return this.getRandomQuizByRetry(userId, false);
    }

    @Override
    public WordQuizResDto randomRetryQuiz(Long userId) {
        return this.getRandomQuizByRetry(userId, true);
    }

    private WordQuizResDto getRandomQuizByRetry(Long userId, boolean retry) {
        List<Dictionary> wordQuizs = (retry ? dictionaryRepository.findRandomRetryWordQuiz(userId) : dictionaryRepository.findRandomWordQuiz(userId)).orElse(null);
        if(CollectionUtils.isEmpty(wordQuizs)) {
            return WordQuizResDto.builder().allSolved(true).build();
        }

        return WordQuizResDto.builder()
                .allSolved(false)
                .entries(wordQuizs.stream().map(this::convertToDto).collect(Collectors.toList())).build();
    }

    public WordQuizEntryDto convertToDto(Dictionary dictionary) {
        return WordQuizEntryDto.builder()
                .wordId(dictionary.getWordId())
                .word(dictionary.getWord())
                .meaning(dictionary.getMeaning())
                .type(dictionary.getType()).build();
    }
}
