package com.it.vh.article.service;

import com.it.vh.article.api.dto.ScrapListResDto;
import com.it.vh.article.domain.entity.Scrap;
import com.it.vh.article.domain.exception.NonExistScrapIdException;
import com.it.vh.article.domain.repository.ScrapRepository;
import com.it.vh.common.exception.AuthenticationAccessForbiddenException;
import com.it.vh.common.util.AuthenticationHandler;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserRespository userRespository;
    private final AuthenticationHandler authenticationHandler;

    private final int SCRAP_PAGE_NUm = 6;

    @Override
    public Page<ScrapListResDto> getScrapListByUserId(long userId, String keyword, int page) throws AuthenticationAccessForbiddenException, NonExistUserIdException {
        // uri로 받은 userId가 로그인한 사용자 ID와 같은지 반드시 체크해야 하는 서비스에서 사용 (정보 수정 삭제 등)
        authenticationHandler.checkUserAuthenticate(userId);

        Optional<User> optionalUser = userRespository.findById(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        String title = "";
        if(keyword != null) title = keyword;
        return scrapRepository.findScrapListByUserIdAndTitle(userId, title, PageRequest.of(page, SCRAP_PAGE_NUm));
    }

    @Override
    public void deleteScrapByScrapId(long scrapId) throws NonExistScrapIdException {
        Optional<Scrap> optionalScrap = scrapRepository.findById(scrapId);
        if(!optionalScrap.isPresent()) throw new NonExistScrapIdException();

        authenticationHandler.checkUserAuthenticate(optionalScrap.get().getUser().getUserId());

        scrapRepository.deleteById(scrapId);
    }
}
