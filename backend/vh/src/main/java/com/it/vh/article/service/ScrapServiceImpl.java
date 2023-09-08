package com.it.vh.article.service;

import com.it.vh.article.api.dto.ScrapListRes;
import com.it.vh.article.domain.repository.ScrapRepository;
import com.it.vh.common.exception.AuthenticationAccessForbiddenException;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserRespository userRespository;

    private final int SCRAP_PAGE_NUm = 8;

    @Override
    public Page<ScrapListRes> getScrapListByUserId(long userId, String keyword, int page) throws AuthenticationAccessForbiddenException, NonExistUserIdException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        long loginId = Long.parseLong(auth.getName());
//        Optional<User> loginUser = userRespository.findUserByUserId(loginId);
//        if(loginUser.get().getUserId() != userId) throw new AuthenticationAccessForbiddenException();
        Optional<User> optionalUser = userRespository.findById(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        String title = "";
        if(keyword != null) title = keyword;
        return scrapRepository.findScrapListByUserIdAndTitle(userId, title, PageRequest.of(page, SCRAP_PAGE_NUm));
    }
}
