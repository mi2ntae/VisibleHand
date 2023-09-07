package com.it.vh.feed.domain.repository;

import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.user.domain.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query(name = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword", nativeQuery = true)
    List<FeedResDto> findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword(long userId, String keyword, boolean isOther, Pageable page);

    @Query(name = "findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword", nativeQuery = true)
    List<FeedResDto> findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword(long userId, String keyword, boolean isOther, Pageable page);

}
