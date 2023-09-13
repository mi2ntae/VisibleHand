package com.it.vh.quiz.service;

import com.it.vh.quiz.api.dto.requestDto.SolvedQuizReq;
import com.it.vh.quiz.domain.exception.SolvingQuizException;
import com.it.vh.user.api.dto.ReviewnoteResDto;
import com.it.vh.user.api.dto.StreakResDto;
import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.exception.NonExistUserIdException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SolvedQuizService {
    Page<ReviewnoteResDto> getReviewNotesByUserId(long userId, int page) throws NonExistUserIdException;

    void solveQuiz(SolvedQuizReq req) throws SolvingQuizException;

    List<StreakResDto> getUserStreak(long userId) throws NonExistUserIdException;
}
