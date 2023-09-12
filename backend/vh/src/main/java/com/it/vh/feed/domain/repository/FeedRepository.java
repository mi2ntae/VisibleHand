package com.it.vh.feed.domain.repository;

import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.user.domain.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
