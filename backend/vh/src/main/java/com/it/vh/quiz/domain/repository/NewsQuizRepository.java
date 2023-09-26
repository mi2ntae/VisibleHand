package com.it.vh.quiz.domain.repository;

import com.it.vh.quiz.domain.entity.NewsQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsQuizRepository extends JpaRepository<NewsQuiz, Long> {
    Optional<NewsQuiz> findByArticle_ArticleId(Long articleId);
}
