package com.it.vh.quiz.api;

import com.it.vh.quiz.api.dto.requestDto.SolvedQuizReq;
import com.it.vh.quiz.api.dto.responseDto.QuizRankResDto;
import com.it.vh.quiz.domain.exception.SolvingQuizException;
import com.it.vh.quiz.service.QuizService;
import com.it.vh.quiz.service.SolvedQuizService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "퀴즈 API", tags = {"Quiz"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final SolvedQuizService solvedQuizService;
    private final QuizService quizService;

    @PutMapping()
    public ResponseEntity<?> solveQuiz (@RequestBody SolvedQuizReq req) throws SolvingQuizException {
        solvedQuizService.solveQuiz(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<?> getNewsquiz(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(quizService.findByArticleId(articleId));
    }

    @GetMapping("/rank")
    public ResponseEntity<List<QuizRankResDto>> getQuizRank() {
        return ResponseEntity.ok().body(quizService.getQuizRank());
    }

    @GetMapping("/dict/{userId}")
    public ResponseEntity<?> getDicQuiz(@PathVariable Long userId) {
        return ResponseEntity.ok().body(quizService.randomQuizs(userId));
    }

    @GetMapping("/retry/{userId}")
    public ResponseEntity<?> getDicRetryQuiz(@PathVariable Long userId) {
        return ResponseEntity.ok().body(quizService.randomRetryQuiz(userId));
    }
}

