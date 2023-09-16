package com.it.vh.user.domain.repository;

import com.it.vh.user.api.dto.UserFollowListResDto;
import com.it.vh.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRespository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserId(long userId);

    Page<User>  findUsersByNicknameContains(String keyword, Pageable page);

    Optional<User> findBySnsEmail(String email);
    Optional<User> findByNickname(String nickname);

    @Query(name = "findRecommendUserByUserId", nativeQuery = true)
    List<UserFollowListResDto> findRecommendUserByUserId(long userId);
}

