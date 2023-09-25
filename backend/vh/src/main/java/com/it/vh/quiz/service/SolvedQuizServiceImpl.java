package com.it.vh.quiz.service;

import com.it.vh.dict.domain.repository.DictionaryRepository;
import com.it.vh.quiz.api.dto.requestDto.SolvedQuizReq;
import com.it.vh.quiz.domain.dto.ArticleQuizCountDto;
import com.it.vh.quiz.domain.dto.UserArticleQuizResDto;
import com.it.vh.quiz.domain.dto.UserWordQuizResDto;
import com.it.vh.quiz.domain.dto.WordQuizCountDto;
import com.it.vh.quiz.domain.entity.SolvedQuiz;
import com.it.vh.quiz.domain.exception.SolvingQuizException;
import com.it.vh.quiz.domain.repository.NewsQuizRepository;
import com.it.vh.quiz.domain.repository.SolvedQuizRepository;
import com.it.vh.user.api.dto.*;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SolvedQuizServiceImpl implements SolvedQuizService {
    private final UserRespository userRespository;
    private final SolvedQuizRepository solvedQuizRepository;
    private final NewsQuizRepository newsQuizRepository;
    private final DictionaryRepository dictionaryRepository;

    private final int[] streakWeight = new int[]{2, 4, 6, 8}; // 1~2 1단계, 3~4 2단계, 5~6 3단계, 7~8 4단계, 9~ 5단계
    private final int REVIEWNOTE_PAGE_NUM = 9;
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

    @Override
    public List<StreakResDto> getUserStreak(long userId) throws NonExistUserIdException {
        Optional<User> optionalUser = userRespository.findById(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        return solvedQuizRepository.findCountOfSolvedQuizByUserId(userId).stream().map(res -> setWeightByScore(res)).collect(Collectors.toList());
    }

    @Override
    public UserQuizStatusResDto getUserQuizStatus(long userId) throws NonExistUserIdException {
        Optional<User> optionalUser = userRespository.findById(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        List<WordQuizCountDto> wordQuizCountDtoList = solvedQuizRepository.countWordSolvedQuizsByCorrectAndUser_UserIdGroupByKind(userId);
        UserWordQuizResDto userWordQuizResDto = UserWordQuizResDto.from(wordQuizCountDtoList);
        List<ArticleQuizCountDto> articleQuizCountDtos = solvedQuizRepository.countArticleSolvedQuizsByCorrectAndUser_UserIdGroupByKind(userId);
        UserArticleQuizResDto articleQuizResDto = UserArticleQuizResDto.from(articleQuizCountDtos);
        return UserQuizStatusResDto.of(userWordQuizResDto, articleQuizResDto);
    }

    private StreakResDto setWeightByScore(StreakResDto streakResDto) {
        for(int i = 0; i < streakWeight.length; i++) {
            if(streakResDto.getWeight() <= streakWeight[i]) {
                streakResDto.setWeight(i+1);
                return streakResDto;
            }
        }
        streakResDto.setWeight(streakWeight.length);
        return streakResDto;
    }
}
