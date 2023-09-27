package com.it.vh.feed.service;

import com.it.vh.common.util.AuthenticationHandler;
import com.it.vh.feed.api.dto.HeartReq;
import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.feed.domain.entity.Heart;
import com.it.vh.feed.domain.repository.FeedRepository;
import com.it.vh.feed.domain.repository.HeartRepository;
import com.it.vh.feed.exception.NonExistFeedIdException;
import com.it.vh.user.domain.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public void createHeartByFeedId(HeartReq heartReq) throws NonExistFeedIdException{
        Optional<Feed> optionalFeed = feedRepository.findById(heartReq.getFeedId());
        if(!optionalFeed.isPresent()) throw new NonExistFeedIdException();
        heartRepository.save(Heart.of(optionalFeed.get(), userRespository.getReferenceById(authenticationHandler.getLoginUserId())));
    }

    @Override
    @Transactional
    public void deleteHeartByFeedId(long feedId) {
        Optional<Feed> optionalFeed = feedRepository.findById(feedId);
        if(!optionalFeed.isPresent()) throw new NonExistFeedIdException();
        heartRepository.deleteByFeed_FeedIdAndUser_UserId(feedId, authenticationHandler.getLoginUserId());
    }

    @Override
    public int getHeartByFeedId(long feedId){
        return heartRepository.getHeartByFeedId(feedId);
    }


}
