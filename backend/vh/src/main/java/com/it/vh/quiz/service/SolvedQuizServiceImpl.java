package com.it.vh.quiz.service;

import com.it.vh.article.domain.repository.ArticleRepository;
import com.it.vh.article.domain.repository.WordCloudRepository;
import com.it.vh.dict.domain.repository.DictionaryRepository;
import com.it.vh.quiz.api.dto.requestDto.SolvedQuizReq;
import com.it.vh.quiz.domain.entity.SolvedQuiz;
import com.it.vh.quiz.domain.exception.SolvingQuizException;
import com.it.vh.quiz.domain.repository.NewsQuizRepository;
import com.it.vh.quiz.domain.repository.SolvedQuizRepository;
import com.it.vh.user.api.dto.ReviewnoteResDto;
import com.it.vh.user.api.dto.UserFollowResDto;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.FollowRepository;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SolvedQuizServiceImpl implements SolvedQuizService {
    private final UserRespository userRespository;
    private final SolvedQuizRepository solvedQuizRepository;
    private final NewsQuizRepository newsQuizRepository;
    private final DictionaryRepository dictionaryRepository;
    private final int REVIEWNOTE_PAGE_NUM = 8;
    @Override
    public Page<ReviewnoteResDto> getReviewNotesByUserId(long userId, int page) throws NonExistUserIdException {
        Optional<User> optionalUser = userRespository.findById(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        return solvedQuizRepository.findByUser_UserIdAndCorrectOrderByCreateAtDesc(userId, false, PageRequest.of(page, REVIEWNOTE_PAGE_NUM))
                .map(solvedQuiz->
                        ReviewnoteResDto.builder()
                            .question(solvedQuiz.getNewsquiz() == null ? solvedQuiz.getWord().getMeaning() : solvedQuiz.getNewsquiz().getQuestion())
                            .answer(solvedQuiz.getNewsquiz() == null ? solvedQuiz.getWord().getWord() : solvedQuiz.getNewsquiz().getAnswer())
                            .build()
                );
    }

    public void solveQuiz(SolvedQuizReq req) throws SolvingQuizException {
        SolvedQuiz sq=new SolvedQuiz();
        sq.setUser(userRespository.getReferenceById(req.getUserId()));
        sq.setCorrect(req.isCorrect());
        if(req.getNewsquizId()!=null){
            sq.setNewsquiz(newsQuizRepository.getReferenceById(req.getNewsquizId()));
        }
        else{
            sq.setWord(dictionaryRepository.getReferenceById(req.getWordId()));
        }
        solvedQuizRepository.save(sq);
    }
}
