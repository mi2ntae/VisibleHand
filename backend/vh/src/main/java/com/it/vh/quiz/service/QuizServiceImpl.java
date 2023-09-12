package com.it.vh.quiz.service;

import com.it.vh.quiz.api.dto.responseDto.NewsQuizRes;
import com.it.vh.quiz.domain.entity.NewsQuiz;
import com.it.vh.quiz.domain.exception.NonExistNewsQuizException;
import com.it.vh.quiz.domain.repository.NewsQuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class QuizServiceImpl implements QuizService{
    private final NewsQuizRepository newsQuizRepository;

    @Override
    public NewsQuizRes findByArticleId(Long articleId) throws NonExistNewsQuizException {
        NewsQuiz nq=newsQuizRepository.findByArticle_ArticleId(articleId);
        NewsQuizRes nqr=new NewsQuizRes();
        nqr.setQuestion(nq.getQuestion());
        nqr.setAnswer(nq.getAnswer());
        nqr.setNewsQuizId(nq.getNewsquizId());
        return nqr;
    }
}
