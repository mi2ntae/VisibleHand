package com.it.vh.feed.service;

import com.it.vh.article.domain.entity.Article;
import com.it.vh.article.domain.repository.ArticleRepository;
import com.it.vh.article.exception.NonExistArticleIdException;
import com.it.vh.common.util.AuthenticationHandler;
import com.it.vh.feed.api.dto.FeedListOfArticleRes;
import com.it.vh.feed.api.dto.FeedListRes;
import com.it.vh.feed.api.dto.FeedRes;
import com.it.vh.feed.api.dto.MyFeedRes;
import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.feed.domain.repository.FeedRepository;
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
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final UserRespository userRespository;
    private final ArticleRepository articleRepository;
    private final AuthenticationHandler authenticationHandler;

    private final int FEED_PAGE_NUM = 6;

    @Override
    public List<FeedRes> getFeedsByUserId(long userId, int searchType, String keyword, int page) {
        Optional<User> optionalUser = userRespository.findUserByUserId(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();

        authenticationHandler.checkUserAuthenticate(userId);
        long myId = authenticationHandler.getLoginUserId();

        List<FeedRes> feedResDtoList = null;
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

    @Override
    public List<FeedListRes> searchFeedsByUserId(long userId, int searchType, String keyword, int page) {
        Optional<User> optionalUser = userRespository.findById(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();

        authenticationHandler.checkUserAuthenticate(userId);
        if(keyword == null) return feedRepository.findFeedsByFollowingUserIdAndTitle(userId, "%%", PageRequest.of(page, FEED_PAGE_NUM));
        switch(searchType) {
            case 0:
                return feedRepository.findFeedsByFollowingUserIdAndTitle(userId, "%" + keyword + "%", PageRequest.of(page, FEED_PAGE_NUM));
            case 1:
                return feedRepository.findFeedsByFollowingUserIdAndContent(userId, "%"+keyword+"%", PageRequest.of(page, FEED_PAGE_NUM));
            default:
                return feedRepository.findFeedsByFollowingUserIdAndTitle(userId, "%%", PageRequest.of(page, FEED_PAGE_NUM));
        }
    }

    @Override
    public List<FeedListOfArticleRes> getFeedsByArticleId(long articleId, long userId, int page) {
        System.out.println(11111);
        Optional<User> optionalUser = userRespository.findUserByUserId(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();
        System.out.println(22222);
        authenticationHandler.checkUserAuthenticate(userId);
        long myId = authenticationHandler.getLoginUserId();
        System.out.println(33333);
        System.out.println(articleId);

        List<FeedListOfArticleRes> list = feedRepository.findFeedsByArticle(articleId, myId, PageRequest.of(page, FEED_PAGE_NUM));
        log.info("list: {}", list);
        return list;
    }

    @Override
    public void registFeed(long userId, long articleId, String content, boolean isShared) {
        Optional<User> optionalUser = userRespository.findById(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();

        authenticationHandler.checkUserAuthenticate(userId);

        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (!optionalArticle.isPresent()) throw new NonExistArticleIdException();

        Feed feed = Feed.builder()
                .article(optionalArticle.get())
                .content(content)
                .share(isShared)
                .user(optionalUser.get())
                .build();

        System.out.println(feed.toString());
        feedRepository.save(feed);
    }

    @Override
    public MyFeedRes getFeedByArticleIdAndUserId(long articleId, long userId) {
        Optional<User> optionalUser = userRespository.findById(userId);
        if(!optionalUser.isPresent()) throw new NonExistUserIdException();

        authenticationHandler.checkUserAuthenticate(userId);

        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (!optionalArticle.isPresent()) throw new NonExistArticleIdException();


        Optional<Feed> optionalFeed = feedRepository.findFeedByArticleAndUser(optionalArticle.get(), optionalUser.get());
        MyFeedRes myFeedRes;
        if (optionalFeed.isPresent()) myFeedRes = MyFeedRes.builder().feedId(optionalFeed.get().getFeedId()).content(optionalFeed.get().getContent()).build();
        else myFeedRes = MyFeedRes.builder().feedId(-1).build();
        return  myFeedRes;
    }

    @Override
    public void updateFeed(long feedId, String content, boolean isShared) {
        Optional<Feed> optionalFeed = feedRepository.findById(feedId);
        if (!optionalFeed.isPresent()) throw new NonExistFeedIdException();
        Feed feed = optionalFeed.get();
        feed.setContent(content);
        feed.setShare(isShared);
        feedRepository.save(feed);
    }

    @Override
    public void deleteFeed(long feedId) {
        if (!feedRepository.existsById(feedId)) throw new NonExistFeedIdException();
        feedRepository.deleteById(feedId);
    }
}
