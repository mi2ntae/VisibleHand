package com.it.vh.feed.domain.repository;

import com.it.vh.feed.domain.entity.Feed;
import com.it.vh.feed.domain.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    void deleteByFeed_FeedIdAndUser_UserId(long feedId, long userId);
}
