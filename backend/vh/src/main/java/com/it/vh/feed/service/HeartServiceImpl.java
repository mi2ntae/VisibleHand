package com.it.vh.feed.service;

import com.it.vh.common.util.AuthenticationHandler;
import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.api.dto.HeartCreateReq;
import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.feed.domain.entity.Heart;
import com.it.vh.feed.domain.repository.FeedRepository;
import com.it.vh.feed.domain.repository.HeartRepository;
import com.it.vh.feed.exception.NonExistFeedIdException;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {
    private final AuthenticationHandler authenticationHandler;
    private final HeartRepository heartRepository;
    private final FeedRepository feedRepository;
    private final UserRespository userRespository;

    @Override
    public void createHeartByFeedId(HeartCreateReq feedHeartCreateReq) throws NonExistFeedIdException{
        Optional<Feed> optionalFeed = feedRepository.findById(feedHeartCreateReq.getFeedId());
        if(!optionalFeed.isPresent()) throw new NonExistFeedIdException();
        heartRepository.save(Heart.of(optionalFeed.get(), userRespository.getReferenceById(authenticationHandler.getLoginUserId())));
    }
}
