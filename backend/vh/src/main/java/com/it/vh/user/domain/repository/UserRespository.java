package com.it.vh.user.domain.repository;

import com.it.vh.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserId(long userId);
    Optional<User> findBySnsEmail(String email);
}
