package com.it.vh.article.service;

import com.it.vh.article.api.dto.ScrapListRes;
import com.it.vh.article.domain.exception.NonExistScrapIdException;
import com.it.vh.common.exception.AuthenticationAccessForbiddenException;
import com.it.vh.user.exception.NonExistUserIdException;
import org.springframework.data.domain.Page;

public interface ScrapService {

    Page<ScrapListRes> getScrapListByUserId(long userId, String keyword, int page) throws AuthenticationAccessForbiddenException, NonExistUserIdException;

    void deleteScrapByScrapId(long scrapId) throws NonExistScrapIdException;
}
