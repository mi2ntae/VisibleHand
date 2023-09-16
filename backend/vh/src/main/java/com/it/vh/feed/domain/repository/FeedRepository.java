package com.it.vh.feed.domain.repository;

import com.it.vh.article.domain.entity.Article;
import com.it.vh.feed.api.dto.FeedListOfArticleRes;
import com.it.vh.feed.api.dto.FeedListRes;
import com.it.vh.feed.api.dto.FeedRes;
import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.user.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query(name = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword", nativeQuery = true)
    List<FeedRes> findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword(long userId, long myId, String keyword, boolean isOther, Pageable page);

    @Query(name = "findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword", nativeQuery = true)
    List<FeedRes> findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword(long userId, long myId, String keyword, boolean isOther, Pageable page);

    @Query(name = "findFeedsByFollowingUserIdAndContent", nativeQuery = true)
    List<FeedListRes> findFeedsByFollowingUserIdAndContent(long userId, String keyword, Pageable page);

    @Query(name = "findFeedsByFollowingUserIdAndTitle", nativeQuery = true)
    List<FeedListRes> findFeedsByFollowingUserIdAndTitle(long userId, String keyword, Pageable page);

    @Query(name = "findFeedsByArticle", nativeQuery = true)
    List<FeedListOfArticleRes> findFeedsByArticle(@Param("articleId") long articleId, @Param("userId") long userId, Pageable page);

    Optional<Feed> findFeedByArticleAndUser(Article article, User user);
}
