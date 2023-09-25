package com.it.vh.quiz.domain.repository;

import com.it.vh.quiz.api.dto.responseDto.QuizRankResDto;
import com.it.vh.quiz.domain.dto.ArticleQuizCountDto;
import com.it.vh.quiz.domain.dto.WordQuizCountDto;
import com.it.vh.quiz.domain.entity.SolvedQuiz;
import com.it.vh.user.api.dto.StreakResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, Long> {

    Page<SolvedQuiz> findByUser_UserIdAndCorrectOrderByCreateAtDesc(long userId, boolean correct, Pageable page);

    @Query(name = "findStreakByUserId", nativeQuery = true)
    List<StreakResDto> findCountOfSolvedQuizByUserId(long userId);

    @Query(value = "SELECT new com.it.vh.quiz.domain.dto.WordQuizCountDto(d.type, count(s.solvedId)) " +
            "FROM solved_quiz s JOIN dictionary d ON s.word.wordId = d.wordId " +
            "WHERE s.correct = true AND s.user.userId = :userId " +
            "GROUP BY d.type")
    List<WordQuizCountDto> countWordSolvedQuizsByCorrectAndUser_UserIdGroupByKind(long userId);

    @Query(value = "SELECT new com.it.vh.quiz.domain.dto.ArticleQuizCountDto(a.kind, count(s.solvedId)) " +
            "FROM solved_quiz s JOIN news_quiz n ON s.newsquiz.newsquizId = n.newsquizId " +
            "JOIN article a ON n.article.articleId = a.articleId " +
            "WHERE s.correct = true AND s.user.userId = :userId " +
            "GROUP BY a.kind")
    List<ArticleQuizCountDto> countArticleSolvedQuizsByCorrectAndUser_UserIdGroupByKind(long userId);

    @Query(value = "SELECT new com.it.vh.quiz.api.dto.responseDto.QuizRankResDto(u.userId, u.nickname, u.profileImg, u.statusMsg, count(s.solvedId)) " +
            "FROM solved_quiz s JOIN s.user u " +
            "WHERE s.createAt BETWEEN :start AND :end AND s.correct = true " +
            "GROUP BY u.userId " +
            "ORDER BY count(s.solvedId) DESC")
    Page<QuizRankResDto> getQuizRank(LocalDateTime start, LocalDateTime end, Pageable page);
}
