package com.it.vh.quiz.domain.repository;

import com.it.vh.quiz.domain.entity.SolvedQuiz;
import com.it.vh.user.api.dto.ReviewnoteResDto;
import com.it.vh.user.domain.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, Long> {

    @Query
    Page<SolvedQuiz> findByUser_UserIdAndCorrectOrderByCreateAtDesc(long userId, boolean correct, Pageable page);

}
