package com.it.vh.quiz.api;

import com.it.vh.quiz.api.dto.requestDto.SolvedQuizReq;
import com.it.vh.quiz.domain.exception.SolvingQuizException;
import com.it.vh.quiz.service.SolvedQuizService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "퀴즈 API", tags = {"Quiz"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final SolvedQuizService solvedQuizService;

    @PutMapping()
    public ResponseEntity<?> solveQuiz (@RequestBody SolvedQuizReq req) throws SolvingQuizException {
        solvedQuizService.solveQuiz(req);
        return ResponseEntity.ok().build();
    }
}