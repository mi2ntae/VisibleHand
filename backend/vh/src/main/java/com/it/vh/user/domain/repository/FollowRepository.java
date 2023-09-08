package com.it.vh.user.domain.repository;

import com.it.vh.user.domain.entity.Follow;
import com.it.vh.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    int countFollowsByFrom_UserId(long fromId);
    int countFollowsByTo_UserId(long toId);

}
