package com.it.vh.feed.domain.repository;

import com.it.vh.article.domain.entity.Article;
import com.it.vh.feed.api.dto.FeedListOfArticleResDto;
import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.api.dto.MyFeedRes;
import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.user.domain.entity.Follow;
import com.it.vh.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query(name = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword", nativeQuery = true)
    List<FeedResDto> findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword(long userId, long myId, String keyword, boolean isOther, Pageable page);

    @Query(name = "findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword", nativeQuery = true)
    List<FeedResDto> findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword(long userId, long myId, String keyword, boolean isOther, Pageable page);

    @Query(name = "findFeedsByFollowingUserIdAndContent", nativeQuery = true)
    List<FeedResDto> findFeedsByFollowingUserIdAndContent(long userId, String keyword, Pageable page);

    @Query(name = "findFeedsByFollowingUserIdAndTitle", nativeQuery = true)
    List<FeedResDto> findFeedsByFollowingUserIdAndTitle(long userId, String keyword, Pageable page);

    /// new io.velog.youmakemesmile.jpql.UserDto(user.id, user.name, user.phone, user.deptId, dept.name) "

    @Query(name = "findFeedsByArticle", nativeQuery = true)
    List<FeedListOfArticleResDto> findFeedsByArticle(@Param("articleId") long articleId, @Param("userId") long userId, Pageable page);



    Optional<Feed> findFeedByArticleAndUser(Article article, User user);

}
