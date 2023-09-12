package com.it.vh.quiz.service;

import com.it.vh.quiz.api.dto.responseDto.NewsQuizRes;
import com.it.vh.quiz.domain.entity.NewsQuiz;

import java.util.Optional;

public interface QuizService {
    public NewsQuizRes findByArticleId(Long articleId);
}
