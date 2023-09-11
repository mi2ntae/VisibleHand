package com.it.vh.user.domain.repository;

import com.it.vh.user.domain.entity.Follow;
import com.it.vh.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    int countFollowsByFrom_UserId(long fromId);
    int countFollowsByTo_UserId(long toId);

    Page<Follow> findFollowsByFrom_UserId(long fromId, Pageable page);
    Page<Follow> findFollowsByTo_UserId(long toId, Pageable page);


}
