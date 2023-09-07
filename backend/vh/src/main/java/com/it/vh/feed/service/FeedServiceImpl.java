package com.it.vh.feed.service;

import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.domain.repository.FeedRepository;
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
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final UserRespository userRespository;
    private final int FEED_PAGE_NUM = 6;

    @Override
    public List<FeedResDto> getFeedsByUserId(long userId, long myId, int searchType, String keyword, int page) {
        Optional<User> optionalUser = userRespository.findUserByUserId(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        Optional<User> optionalMyUser = userRespository.findUserByUserId(myId);
        if(!optionalMyUser.isPresent()) throw new NonExistUserIdException();
        
        List<FeedResDto> feedResDtoList = null;
        boolean isMe = userId == myId ? true : false;
        if(keyword == null) return feedRepository.findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword(userId, myId, "%%", !isMe, PageRequest.of(page, FEED_PAGE_NUM));
        switch(searchType) {
            case 0:
                feedResDtoList = feedRepository.findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword(userId, myId,"%"+keyword+"%", !isMe, PageRequest.of(page, FEED_PAGE_NUM));
                break;
            case 1:
                feedResDtoList = feedRepository.findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword(userId, myId,"%"+keyword+"%", !isMe, PageRequest.of(page, FEED_PAGE_NUM));
                break;
            default:
                feedResDtoList = feedRepository.findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword(userId, myId,"%%", !isMe, PageRequest.of(page, FEED_PAGE_NUM));
                break;
        }
        return feedResDtoList;
    }
}
